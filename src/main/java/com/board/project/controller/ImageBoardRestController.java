package com.board.project.controller;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageData;
import com.board.project.domain.ImageDataDTO;
import com.board.project.domain.Member;
import com.board.project.repository.ImageDataRepository;
import com.board.project.service.ImageBoardService;
import com.board.project.service.PrincipalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardRestController {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private ImageBoardService imageBoardService;

    //이미지 상세페이지에서 받을 이미지 파일 리스트
    @GetMapping("/imageList")
    @ResponseBody
    public ResponseEntity<List<ImageDataDTO>> detailImageList(long imageNo){
        /**
         * imageNo 받아서 처리
         */

        log.info("rest imageList");

        log.info("detailImageList imageNo : " + imageNo);

//        List<ImageData> list = imageDataRepository.imageDataList(imageNo);

//        log.info("test : " + new ResponseEntity<>(imageDataRepository.imageDataList(imageNo), HttpStatus.OK));


        return new ResponseEntity<>(imageDataRepository.imageDataList(imageNo),HttpStatus.OK);
    }

    //이미지 게시판 작성
    @PostMapping("/imageInsert")
    @ResponseBody
    public void imageBoardInsert(@RequestParam("files")List<MultipartFile> images
                                    , HttpServletRequest request
                                    , ImageBoard imageBoard
                                    , ImageData imageData
                                    , Principal principal) throws Exception{
        /**
         * imageList로 이동
         */
        log.info("rest imageInsert");

        imageBoardService.imageSizeCheck(images);

        imageBoardService.imageInsertCheck(images, request, imageBoard, imageData, principal);

    }

    //이미지 게시판 수정
    @PutMapping("/imageModify")
    public void imageBoardModify(@RequestParam(value = "files", required = false) List<MultipartFile> images
                                    , @RequestParam(value = "deleteFiles", required = false) List<String> deleteFiles
                                    , HttpServletRequest request
                                    , ImageBoard imageBoard
                                    , ImageData imageData) throws Exception{
        /**
         * imageDetail로 이동
         */

        log.info("rest imageModify");

        if(images.size() != 0)
            imageBoardService.imageSizeCheck(images);

        imageBoardService.imageModifyCheck(images, deleteFiles, request, imageBoard, imageData);

    }

    //이미지 게시판 삭제
    @DeleteMapping("/imageDelete")
    @ResponseBody
    public void imageBoardDelete(@RequestBody String imageNo){
        /**
         * imageNo 받아서 처리
         * 처리 후 imageList로 이동
         */

        log.info("rest imageDelete");

        log.info("imageNo : " + imageNo);
    }



}
