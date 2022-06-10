package com.board.project.controller;


import com.board.project.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentRestController {


    //댓글 리스트
    @GetMapping("/commentList")
    public ResponseEntity<List<Comment>> commentList(){

        /**
         * 각 게시판 No 받아서 처리.
         */
        log.info("comment List");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //댓글 작성
    @PostMapping("/commentInsert")
    public void commentInsert(){
        log.info("commnet Insert");
    }

    //대댓글 작성
    @PostMapping("/commentReply")
    public void commentReply(){
        log.info("commentReply");
    }

    //댓글 삭제
    @DeleteMapping("/commentDelete")
    public void commentDelete(){
        log.info("comment Delete");
    }
}
