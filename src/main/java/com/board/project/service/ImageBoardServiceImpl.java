package com.board.project.service;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageData;
import com.board.project.domain.Member;
import com.board.project.properties.ImageSizeProperties;
import com.board.project.repository.ImageBoardRepository;
import com.board.project.repository.ImageDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.sql.Date;
import java.sql.SQLException;
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

    @Override
    public int imageSizeCheck(List<MultipartFile> images) throws Exception {
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
    @Transactional
    public int imageInsertCheck(List<MultipartFile> images, HttpServletRequest request, ImageBoard imageBoard, ImageData imageData, Principal principal) throws Exception{
        log.info("imageInsertCheck");

        log.info("images size : " + images.size());
        images.forEach(s -> log.info("imageInsertCheck file : " + s.getOriginalFilename()));

        log.info("imageTitle : " + request.getParameter("imageTitle"));
        log.info("imageContent : " + request.getParameter("imageContent"));




        try{
            ImageBoard.builder()
                    .member(principalService.checkPrincipal(principal))
                    .imageTitle(request.getParameter("imageTitle"))
                    .imageContent(request.getParameter("imageContent"))
                    .imageDate(Date.valueOf(LocalDate.now()))
                    .build();

            imageData = imageInsert(images, request, imageData, 0);
        }catch (Exception e){

        }


        return 0;
    }

    @Override
    public int imageModifyCheck(List<MultipartFile> images, List<String> deleteFiles, HttpServletRequest request, ImageBoard imageBoard, ImageData imageData) throws Exception {
        log.info("imageModifyCheck");

        images.forEach(s -> log.info("imageModify files : " + s.getOriginalFilename()));
        deleteFiles.forEach(s -> log.info("imageModify deleteFiles : " + s));

        log.info("imageTitle : " + request.getParameter("imageTitle"));
        log.info("imageContent : " + request.getParameter("imageContent"));
        return 0;
    }

    @Override
    public void deleteImageBoard(long imageNo, HttpServletRequest request) throws Exception {
        log.info("delete imageBoard");

        log.info("delete imageNo : " + imageNo);
    }

    //이미지 파일 저장 및 ImageData save
    ImageData imageInsert(List<MultipartFile> images, HttpServletRequest request, ImageData imageData, int step) throws Exception {

        String filePath = request.getSession().getServletContext().getRealPath("/img/");

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

                return ImageData.builder().imageName(saveName).oldName(originalName).imageStep(step).build();
            }catch (Exception e){
                throw new SQLException();
            }
        }

        return imageData;
    }

    //이미지 파일 삭제
    void deleteFiles(List<String> deleteFiles, HttpServletRequest request) throws Exception {


    }
}
