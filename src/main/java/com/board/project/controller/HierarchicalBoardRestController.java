package com.board.project.controller;

import com.board.project.domain.HierarchicalBoard;
import com.board.project.domain.Member;
import com.board.project.repository.HierarchicalBoardRepository;
import com.board.project.service.HierarchicalBoardService;
import com.board.project.service.PrincipalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String hierarchicalBoardInsert(HttpServletRequest request, Principal principal) throws Exception{
        /**
         * insert 처리 후 boardList로 이동
         */

        log.info("rest boardInsert");

        log.info("title :  "  +request.getParameter("boardTitle"));
        log.info("content : " + request.getParameter("boardContent"));

        hierarchicalBoardService.insertBoard(request, principal);

        return "redirect:/board/boardList";

    }

    //게시글 수정 처리
    @PutMapping("/boardModify")
    public String hierarchicalBoardModify(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception {
        /**
         * update 처리 후 boardDetail로 이동
         */

        log.info("rest boardModify");

        log.info("request title : " + request.getParameter("boardTitle"));
        log.info("request boardNo : " + request.getParameter("boardNo"));
        log.info("request content :  " + request.getParameter("boardContent"));

        String boardTitle = request.getParameter("boardTitle");
        String boardContent = request.getParameter("boardContent");
        long boardNo = Long.parseLong(request.getParameter("boardNo"));


        hierarchicalBoardRepository.boardModify(boardTitle, boardContent, boardNo);

        return "redirect:/board/boardDetail/"+boardNo;

    }

    //게시글 삭제 처리
    @DeleteMapping("/boardDelete")
    public void hierarchicalBoardDelete(@RequestBody String boardNo) throws Exception{
        /**
         * 삭제 처리 후 boardList로 이동
         */

        log.info("rest boardDelete");

        log.info("boardDelete no : " + boardNo);

        ObjectMapper om = new ObjectMapper();
        HierarchicalBoard hierarchicalBoard = om.readValue(boardNo, HierarchicalBoard.class);

        log.info("boardNo : " + hierarchicalBoard.getBoardNo());

        hierarchicalBoardService.deleteBoard(hierarchicalBoard);
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
