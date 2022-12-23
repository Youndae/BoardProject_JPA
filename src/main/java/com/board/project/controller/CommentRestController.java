package com.board.project.controller;


import com.board.project.domain.Comment;
import com.board.project.domain.Member;
import com.board.project.service.CommentService;
import com.board.project.service.PrincipalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentRestController {

    private CommentService commentService;

    private PrincipalService principalService;

    //댓글 작성
    @PostMapping("/commentInsert")
    public int commentInsert(@RequestBody Map<String, Object> commentData
                                , Comment comment
                                , Principal principal) throws Exception{
        log.info("commnet Insert");

        //principal check
        Member member = principalService.checkPrincipal(principal);

        /*if(member == null)
            return 0; //principal error == 0
        else
            comment.setMember(member);*/

        commentData.forEach((s, o) -> log.info("comment insert " + s + " : " + o));

        return commentService.commentInsert(commentData, comment, principal);

    }

    //대댓글 작성
    @PostMapping("/commentReply")
    public int commentReply(@RequestBody Map<String, Object> commentData
                                , Comment comment
                                , Principal principal)throws Exception{
        log.info("commentReply");

        //principal check
        Member member = principalService.checkPrincipal(principal);

        /*if(member == null)
            return 0; //principal error == 0
        else
            comment.setMember(member);*/

        return commentService.commentReplyInsert(commentData, comment, principal);

    }

    //댓글 삭제
    @DeleteMapping("/commentDelete")
    public int commentDelete(@RequestBody String commentNo) throws Exception{
        log.info("comment Delete");

        log.info("comment delete commentNo : " + commentNo);

        ObjectMapper om = new ObjectMapper();
        Comment comment = om.readValue(commentNo, Comment.class);
        log.info("commentNo : "+ comment.getCommentNo());

        return commentService.commentDelete(comment);

    }
}
