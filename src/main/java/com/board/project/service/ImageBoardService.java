package com.board.project.service;

import com.board.project.domain.dto.ImageBoardDTO;
import com.board.project.domain.dto.ImageBoardModifyDTO;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.entity.ImageBoard;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface ImageBoardService {

    //이미지 게시글 리스트
    Page<ImageBoardDTO> getImageList(Criteria cri);

    //이미지 게시글 작성
    long imageInsertCheck(List<MultipartFile> images
            , HttpServletRequest request
            , Principal principal);

    //이미지 게시글 수정
    long imageModifyCheck(List<MultipartFile> images
            , List<String> deleteFiles
            , HttpServletRequest request, Principal principal) throws Exception;

    //이미지 게시글 삭제
    long deleteImageBoard(long imageNo, HttpServletRequest request, Principal principal) throws Exception;


    //이미지 게시글 수정 데이터 get
    ImageBoardModifyDTO getImageModifyData(long imageNo, Principal principal);


}
