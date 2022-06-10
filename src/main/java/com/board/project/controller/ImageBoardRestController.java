package com.board.project.controller;

import com.board.project.domain.ImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardRestController {

    //이미지 상세페이지에서 받을 이미지 파일 리스트
    @GetMapping("/imageList")
    public ResponseEntity<List<ImageData>> detailImageList(){
        /**
         * imageNo 받아서 처리
         */

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //이미지 게시판 작성
    @PostMapping("/imageInsert")
    public void imageBoardInsert(){
        /**
         * imageList로 이동
         */
    }

    //이미지 게시판 수정
    @PutMapping("/imageModify")
    public void imageBoardModify(){
        /**
         * imageDetail로 이동
         */
    }

    //이미지 게시판 삭제
    @DeleteMapping("/imageDelete")
    public void imageBoardDelete(){
        /**
         * imageNo 받아서 처리
         * 처리 후 imageList로 이동
         */
    }



}
