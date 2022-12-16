package com.board.project.service;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface ImageBoardService {

    //이미지파일 사이즈 체크
    int imageSizeCheck(List<MultipartFile> images) throws Exception;

    //이미지 게시글 작성
    int imageInsertCheck(List<MultipartFile> images
            , HttpServletRequest request
            , ImageBoard imageBoard
            , ImageData imageData
            , Principal principal);

    //이미지 게시글 수정
    int imageModifyCheck(List<MultipartFile> images
            , List<String> deleteFiles
            , HttpServletRequest request
            , ImageBoard imageBoard
            , ImageData imageData) throws Exception;

    //이미지 게시글 삭제
    void deleteImageBoard(long imageNo, HttpServletRequest request) throws Exception;


}
