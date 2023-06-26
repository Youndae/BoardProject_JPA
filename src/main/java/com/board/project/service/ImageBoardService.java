package com.board.project.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface ImageBoardService {

    //이미지 게시글 작성
    long imageInsertCheck(List<MultipartFile> images
            , HttpServletRequest request
            , Principal principal);

    //이미지 게시글 수정
    long imageModifyCheck(List<MultipartFile> images
            , List<String> deleteFiles
            , HttpServletRequest request, Principal principal) throws Exception;

    //이미지 게시글 삭제
    long deleteImageBoard(long imageNo, HttpServletRequest request) throws Exception;


}
