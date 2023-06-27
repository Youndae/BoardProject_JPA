package com.board.project.controller;

import com.board.project.domain.dto.BoardCommentDTO;
import com.board.project.domain.dto.HierarchicalBoardDTO;
import com.board.project.domain.dto.HierarchicalBoardModifyDTO;
import com.board.project.domain.entity.Comment;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.dto.PageDTO;
import com.board.project.repository.CommentRepository;
import com.board.project.repository.HierarchicalBoardRepository;
import com.board.project.service.HierarchicalBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class HierarchicalBoardController {


    private final HierarchicalBoardRepository hierarchicalBoardRepository;

    private final CommentRepository commentRepository;

    private final HierarchicalBoardService hierarchicalBoardService;


    //계층형 게시판 리스트(메인)
    @RequestMapping(method = RequestMethod.GET, value = "/boardList")
    public String hierarchicalBoardMain(Model model
                                        , Criteria cri) {

        Page<HierarchicalBoardDTO> dto = hierarchicalBoardService.getHierarchicalBoardList(cri);

        model.addAttribute("boardList",dto.getContent());

        model.addAttribute("pageMaker", new PageDTO(cri, dto.getTotalPages()));

        log.info("boardList log");

        return "th/board/boardList";
    }

    //계층형 게시판 상세페이지
    @GetMapping("/boardDetail/{boardNo}")
    public String hierarchicalBoardDetail(Model model
                                    , Principal principal
                                    , @PathVariable long boardNo
                                    , @ModelAttribute("comment")Comment comment
                                    , Criteria cri
                                    , HttpSession session) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardDetail");

        log.info("boardNo : " + boardNo);

        if(principal != null)
            session.setAttribute("userId", principal.getName());

        model.addAttribute("boardDetail", hierarchicalBoardRepository.findByBoardNo(boardNo));

        Page<BoardCommentDTO> commentDTO = commentRepository.hierarchicalCommentList(boardNo
                                            , PageRequest.of(cri.getPageNum() - 1
                                                    , cri.getBoardAmount()
                                                    , Sort.by("commentGroupNo").ascending()
                                                            .and(Sort.by("commentUpperNo").ascending())));

        model.addAttribute("comment", commentDTO.getContent());

        /*if(principal == null)
            log.info("Principal is null");
        else if(principal != null)
            log.info("Principal is not null " + principal.getName());*/

        model.addAttribute("pageMaker", new PageDTO(cri, commentDTO.getTotalPages()));

        return "th/board/boardDetail";
    }

    //계층형 게시판 수정페이지
    @GetMapping("/boardModify/{boardNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String hierarchicalBoardModify(Model model, @PathVariable long boardNo, Principal principal) {
        /**
         * boardNo 받아서 처리
         */

        HierarchicalBoardModifyDTO dto = hierarchicalBoardService.getModifyData(boardNo, principal);

        if(dto == null){
            return "th/error/error";
        }else{
            model.addAttribute("boardModify", dto);

            log.info("boardModify");

            return "th/board/boardModify";
        }
    }

    //계층형 게시판 글작성
    @GetMapping("/boardInsert")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String hierarchicalBoardInsert() {
        log.info("boardInsert");

        return "th/board/boardInsert";
    }

    //계층형 게시판 답글 작성
    @GetMapping("/boardReply/{boardNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String hierarchicalBoardReply(Model model, @PathVariable long boardNo) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardReply");

        model.addAttribute("boardReply", hierarchicalBoardRepository.findByBoardNo(boardNo));

        return "th/board/boardReply";
    }

}
