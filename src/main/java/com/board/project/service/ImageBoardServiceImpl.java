package com.board.project.service;

import com.board.project.domain.dto.ImageBoardDTO;
import com.board.project.domain.dto.ImageBoardModifyDTO;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.entity.ImageBoard;
import com.board.project.domain.entity.ImageData;
import com.board.project.properties.ImageSizeProperties;
import com.board.project.repository.ImageBoardRepository;
import com.board.project.repository.ImageDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageBoardServiceImpl implements ImageBoardService{

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private PrincipalService principalService;

    private static long sizeSum = 0;

    //이미지 게시판 리스트
    @Override
    public Page<ImageBoardDTO> getImageList(Criteria cri) {

        String keyword = null;

        if(cri.getKeyword() != null)
            keyword = "%" + cri.getKeyword() + "%";

        Page<ImageBoardDTO> dto;

        if(cri.getKeyword() == null || cri.getKeyword() == ""){//default
            dto =
                    imageBoardRepository.getImageBoardList(
                        PageRequest.of(cri.getPageNum() - 1
                                , cri.getImageAmount()
                                , Sort.by("imageNo").descending())
            );
        }else if(cri.getSearchType().equals("t")){//제목 검색
            dto =
                    imageBoardRepository.getImageBoardSearchTitle(
                        keyword
                        , PageRequest.of(cri.getPageNum() - 1
                                , cri.getImageAmount()
                                , Sort.by("imageNo").descending())
            );
        }else if(cri.getSearchType().equals("c")){//내용 검색
            dto =
                    imageBoardRepository.getImageBoardSearchContent(
                        keyword
                        , PageRequest.of(cri.getPageNum() - 1
                                , cri.getImageAmount()
                                , Sort.by("imageNo").descending())
            );
        }else if(cri.getSearchType().equals("u")){//작성자 검색
            dto =
                    imageBoardRepository.getImageBoardSearchWriter(
                        keyword
                        , PageRequest.of(cri.getPageNum() - 1
                                , cri.getImageAmount()
                                , Sort.by("imageNo").descending())
            );
        }else if(cri.getSearchType().equals("tc")){//제목 + 내용 검색
            dto =
                    imageBoardRepository.getImageBoardSearchTitleAndContent(
                        keyword
                        , PageRequest.of(cri.getPageNum() - 1
                                , cri.getImageAmount()
                                , Sort.by("imageNo").descending())
            );
        }else{
            dto = null;
        }

        return dto;
    }

    //이미지파일 사이즈 체크
    public long imageSizeCheck(List<MultipartFile> images) throws Exception {
        log.info("image Size check");
        /*log.info("images size : " + images.size());
        images.forEach(s -> log.info("imageFile : " + s));*/

        for(MultipartFile image : images){
            sizeSum += image.getSize();
            log.info("sizeSum : " + sizeSum);
            if(sizeSum >= ImageSizeProperties.LIMIT_SIZE){
                log.info("image size is larger than the limit size");
                return ImageSizeProperties.RESULT_EXCEED_SIZE;
            }
        }
        log.info("image size check success");
        return ImageSizeProperties.RESULT_SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long imageInsertCheck(List<MultipartFile> images, HttpServletRequest request, Principal principal) {
        log.info("imageInsertCheck");

        log.info("images size : " + images.size());
        images.forEach(s -> log.info("imageInsertCheck file : " + s.getOriginalFilename()));

        try{
            if(imageSizeCheck(images) == ImageSizeProperties.RESULT_EXCEED_SIZE){
                return ImageSizeProperties.RESULT_EXCEED_SIZE;
            }
        }catch (Exception e){
            log.info("sizeCheck Exception");
            return -1;
        }


        log.info("imageTitle : " + request.getParameter("imageTitle"));
        log.info("imageContent : " + request.getParameter("imageContent"));

        try{
            ImageBoard imageBoard = ImageBoard.builder()
                    .member(principalService.checkPrincipal(principal))
                    .imageTitle(request.getParameter("imageTitle"))
                    .imageContent(request.getParameter("imageContent"))
                    .imageDate(Date.valueOf(LocalDate.now()))
                    .build();

            imageInsert(images, request, 1, imageBoard);

            return imageBoardRepository.save(imageBoard).getImageNo();

        }catch (Exception e){
            log.info("insertion exception!!");
            return -1;
        }

    }

    @Override
    public long imageModifyCheck(List<MultipartFile> images, List<String> deleteFiles
                                , HttpServletRequest request, Principal principal) throws Exception {
        log.info("imageModifyCheck");

        try{
            if(images != null){
                if(imageSizeCheck(images) == ImageSizeProperties.RESULT_EXCEED_SIZE){
                    return ImageSizeProperties.RESULT_EXCEED_SIZE;
                }
            }
        }catch (Exception e){
            log.info("Modify sizeCheck Exception");
            return -1;
        }

        long imageNo = Long.parseLong(request.getParameter("imageNo"));

        try{
            ImageBoard imageBoard = ImageBoard.builder()
                    .member(principalService.checkPrincipal(principal))
                    .imageNo(imageNo)
                    .imageTitle(request.getParameter("imageTitle"))
                    .imageContent(request.getParameter("imageContent"))
                    .imageDate(Date.valueOf(request.getParameter("imageDate")))
                    .build();

            if(images != null)
                imageInsert(images, request, imageDataRepository.countImageStep(imageNo) + 1, imageBoard);

            //delete imageData 필요.
            if(deleteFiles != null)
                deleteFilesProc(deleteFiles, request);

            return imageBoardRepository.save(imageBoard).getImageNo();
        }catch (Exception e){
            log.info("modify exception");
            return -1;
        }
    }

    @Override
    public long deleteImageBoard(long imageNo, HttpServletRequest request, Principal principal) throws Exception {
        log.info("delete imageBoard");

        log.info("delete imageNo : " + imageNo);

        if(principal == null || !checkWriter(imageNo, principal))
            return 0;

        try{
            List<String> deleteFileName = imageDataRepository.deleteImageDataList(imageNo);

            deleteFilesProc(deleteFileName, request);

            imageBoardRepository.deleteById(imageNo);

            return 1;
        }catch (Exception e){
            log.info("deleteBoard Exception");
            return -1;
        }

    }

    @Override
    public ImageBoardModifyDTO getImageModifyData(long imageNo, Principal principal) {

        if(principal == null || !checkWriter(imageNo, principal))
            return null;
        else
            return imageBoardRepository.modifyImageDetail(imageNo);
    }

    //이미지 파일 저장 및 ImageData save
    void imageInsert(List<MultipartFile> images, HttpServletRequest request, int step, ImageBoard imageBoard) throws Exception{

        String filePath = request.getSession().getServletContext().getRealPath("/img/");

        log.info("file path : " + filePath);

        for(MultipartFile image : images){
            String originalName = image.getOriginalFilename();

            try{
                StringBuffer sb = new StringBuffer();
                String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
                        .append(UUID.randomUUID().toString())
                        .append(originalName.substring(originalName.lastIndexOf("."))).toString();
                String saveFile = filePath + saveName;

                image.transferTo(new File(saveFile));
                log.info("save End");

                log.info("saveName : "+saveName+", OldName : "+originalName+", imageStep : "+step);

                ImageData imageData = ImageData.builder()
                        .imageName(saveName)
                        .oldName(originalName)
                        .imageStep(step)
                        .build();

                imageBoard.addImageData(imageData);

                step++;

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    //이미지 파일 삭제
    void deleteFilesProc(List<String> deleteFiles, HttpServletRequest request) throws Exception {
        String filePath = request.getSession().getServletContext().getRealPath("/img/");

        try{
            for(int i = 0; i < deleteFiles.size(); i++){
                imageDataRepository.deleteById(deleteFiles.get(i));
                File file = new File(filePath + deleteFiles.get(i));

                if(file.exists()){
                    file.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //이미지게시판 사용자, 작성자 체크
    boolean checkWriter(long imageNo, Principal principal){

        if(principal.getName().equals(imageBoardRepository.checkWriter(imageNo)))
            return true;

        return false;
    }
}
