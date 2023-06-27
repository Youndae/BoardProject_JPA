package com.board.project.controller;

import com.board.project.domain.entity.ImageBoard;
import com.board.project.domain.dto.ImageDataDTO;
import com.board.project.repository.ImageDataRepository;
import com.board.project.service.ImageBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/imageBoard")
@RequiredArgsConstructor
public class ImageBoardRestController {


    private final ImageDataRepository imageDataRepository;


    private final ImageBoardService imageBoardService;

    //이미지 상세페이지에서 받을 이미지 파일 리스트
    @GetMapping("/imageList")
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
    public long imageBoardInsert(@RequestParam("files")List<MultipartFile> images
                                    , HttpServletRequest request
                                    , Principal principal) throws Exception{
        /**
         * imageList로 이동
         */
        log.info("rest imageInsert");



        return imageBoardService.imageInsertCheck(images, request, principal);

    }

    //이미지 게시판 수정
    @PutMapping("/imageModify")
    public long imageBoardModify(@RequestParam(value = "files", required = false) List<MultipartFile> images
                                    , @RequestParam(value = "deleteFiles", required = false) List<String> deleteFiles
                                    , HttpServletRequest request
                                    , Principal principal) throws Exception{
        /**
         * imageDetail로 이동
         */

        log.info("rest imageModify");

        return imageBoardService.imageModifyCheck(images, deleteFiles, request, principal);

    }

    //이미지 게시판 삭제
    @DeleteMapping("/imageDelete/{imageNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public long imageBoardDelete(@PathVariable("imageNo") long imageNo, HttpServletRequest request, Principal principal) throws Exception {
        /**
         * imageNo 받아서 처리
         * 처리 후 imageList로 이동
         */

        log.info("rest imageDelete");

        log.info("imageNo : " + imageNo);


        return imageBoardService.deleteImageBoard(imageNo, request, principal);
    }



}
