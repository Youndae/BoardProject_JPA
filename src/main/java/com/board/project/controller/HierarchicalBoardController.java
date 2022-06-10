package com.board.project.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/board")
public class HierarchicalBoardController {

    //계층형 게시판 리스트(메인)
    @GetMapping("/boardList")
    public void hierarchicalBoardMain(Model model){

    }

    //계층형 게시판 상세페이지
    @GetMapping("/boardDetail")
    public void hierarchicalBoardDetail(Model model){
        /**
         * boardNo 받아서 처리
         */
    }

    //계층형 게시판 수정페이지
    @GetMapping("/boardModify")
    public void hierarchicalBoardModify(Model model){
        /**
         * boardNo 받아서 처리
         */
    }

    //계층형 게시판 글작성
    @GetMapping("/boardInsert")
    public void hierarchicalBoardInsert(){

    }

    //계층형 게시판 답글 작성
    @GetMapping("/boardReply")
    public void hierarchicalBoardReply(Model model){
        /**
         * boardNo 받아서 처리
         */
    }

}
