package com.board.project.controller;

import com.board.project.domain.ImageData;
import com.board.project.repository.ImageDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardRestController {

    @Autowired
    private ImageDataRepository imageDataRepository;

    //이미지 상세페이지에서 받을 이미지 파일 리스트
    @GetMapping("/imageList")
    @ResponseBody
    public ResponseEntity<List<ImageData>> detailImageList(long imageNo){
        /**
         * imageNo 받아서 처리
         */

        log.info("rest imageList");

        log.info("detailImageList imageNo : " + imageNo);


        return new ResponseEntity<>(imageDataRepository.imageDataList(imageNo),HttpStatus.OK);
    }

    //이미지 게시판 작성
    @PostMapping("/imageInsert")
    public void imageBoardInsert(){
        /**
         * imageList로 이동
         */

        log.info("rest imageInsert");
    }

    //이미지 게시판 수정
    @PutMapping("/imageModify")
    public void imageBoardModify(){
        /**
         * imageDetail로 이동
         */

        log.info("rest imageModify");
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
