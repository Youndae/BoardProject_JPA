package com.board.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/board")
public class HierarchicalBoardRestController {


    //게시글 작성 처리
    @PostMapping("/boardInsert")
    public void hierarchicalBoardInsert(){
        /**
         * insert 처리 후 boardList로 이동
         */
    }

    //게시글 수정 처리
    @PutMapping("/boardModify")
    public void hierarchicalBoardModify(){
        /**
         * update 처리 후 boardDetail로 이동
         */
    }

    //게시글 삭제 처리
    @DeleteMapping("/boardDelete")
    public void hierarchicalBoardDelete(){
        /**
         * 삭제 처리 후 boardList로 이동
         */
    }

    //게시글 답글 작성 처리
    @PostMapping("/boardReply")
    public void hierarchicalBoardReply(){
        /**
         * 답글 작성 처리 후 boardList로 이동
         */
    }
}
