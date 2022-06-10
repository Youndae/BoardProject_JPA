package com.board.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardController {


    //이미지 게시판 리스트(메인)
    @GetMapping("/imageBoardList")
    public void imageBoardMain(HttpServletRequest request, Model model){

        String filePath = request.getSession().getServletContext().getRealPath("img/");

        log.info("filepath : " + filePath);

    }

    //이미지 게시판 상세
    @GetMapping("/imageBoardDetail")
    public void imageBoardDetail(Model model){
        /**
         * boardNo 받아서 처리
         */
    }

    //이미지 게시판 작성
    @GetMapping("/imageBoardInsert")
    public void imageBoardInsert(){

    }

    //이미지 게시판 수정
    @GetMapping("/imageBoardModify")
    public void imageBoardModify(Model model){
        /**
         * boardNo 받아서 처리
         */
    }







}
