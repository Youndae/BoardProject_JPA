package com.board.project.controller;

import com.board.project.domain.entity.HierarchicalBoard;
import com.board.project.repository.HierarchicalBoardRepository;
import com.board.project.service.HierarchicalBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class HierarchicalBoardRestController {


    private final HierarchicalBoardRepository hierarchicalBoardRepository;

    private final HierarchicalBoardService hierarchicalBoardService;


    //게시글 작성 처리
    @PostMapping("/boardInsert")
    public ResponseEntity<?> hierarchicalBoardInsert(HttpServletRequest request, Principal principal) throws Exception{
        /**
         * insert 처리 후 boardList로 이동
         */

        log.info("rest boardInsert");

        log.info("title :  "  +request.getParameter("boardTitle"));
        log.info("content : " + request.getParameter("boardContent"));

        long boardNo = hierarchicalBoardService.insertBoard(request, principal);

        HttpHeaders headers = new HttpHeaders();
        if(boardNo != -1) {
            headers.setLocation(URI.create("/board/boardDetail/" + boardNo));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }else {
            headers.setLocation(URI.create("/error/error"));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }


    }

    //게시글 수정 처리
    @PutMapping("/boardModify")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<?> hierarchicalBoardModify(HttpServletRequest request) throws Exception {
        /**
         * update 처리 후 boardDetail로 이동
         */

        log.info("rest boardModify");

        long boardNo = hierarchicalBoardService.boardModify(request);

        log.info("modify boardNo : {}", boardNo);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/board/boardDetail/" + boardNo));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    //게시글 삭제 처리
    @DeleteMapping("/boardDelete/{boardNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public long hierarchicalBoardDelete(@PathVariable long boardNo, Principal principal) throws Exception{
        /**
         * 삭제 처리 후 boardList로 이동
         */

        log.info("rest boardDelete");

        log.info("boardDelete no : " + boardNo);

        /*ObjectMapper om = new ObjectMapper();
        HierarchicalBoard hierarchicalBoard = om.readValue(boardNo, HierarchicalBoard.class);

        log.info("boardNo : " + hierarchicalBoard.getBoardNo());*/

        return hierarchicalBoardService.deleteBoard(boardNo, principal);
    }

    //게시글 답글 작성 처리
    @PostMapping("/boardReply")
    public void hierarchicalBoardReply(HttpServletRequest request, Principal principal) throws Exception{
        /**
         * 답글 작성 처리 후 boardList로 이동
         */

        log.info("rest boardReply");

        log.info("title :  "  +request.getParameter("boardTitle"));
        log.info("content : " + request.getParameter("boardContent"));
        log.info("groupNo : " + request.getParameter("boardGroupNo"));
        log.info("upperNo : " + request.getParameter("boardUpperNo"));

//        hierarchicalBoard.setMember(principalService.checkPrincipal(principal));
        hierarchicalBoardService.insertBoardReply(request, principal);
    }
}
