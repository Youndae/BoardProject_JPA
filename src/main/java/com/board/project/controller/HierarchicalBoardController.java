package com.board.project.controller;

import com.board.project.domain.Comment;
import com.board.project.domain.Criteria;
import com.board.project.domain.PageDTO;
import com.board.project.repository.CommentRepository;
import com.board.project.repository.HierarchicalBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    //계층형 게시판 리스트(메인)
    @RequestMapping(method = RequestMethod.GET, value = "/boardList")
    public String hierarchicalBoardMain(Model model, Criteria cri) {

        if (cri.getKeyword() == null || cri.getKeyword() == "") {// default List
            model.addAttribute("boardList",
                    hierarchicalBoardRepository.hierarchicalBoardList(
                            PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "t") {//title 검색시 사용
            model.addAttribute("boardList",
                    hierarchicalBoardRepository.hierarchicalBoardListSearchTitle(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "c") {//content 검색시 사용
            model.addAttribute("boardList",
                    hierarchicalBoardRepository.hierarchicalBoardListSearchContent(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "u") {// user 검색 시 사용
            model.addAttribute("boardList",
                    hierarchicalBoardRepository.hierarchicalBoardListSearchUser(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getKeyword() == "tc") {// title and content 검색시 사용
            model.addAttribute("boardList",
                    hierarchicalBoardRepository.hierarchicalBoardListSearchTitleOrContent(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        }


        log.info("boardList log");

        return "th/board/boardList";
    }

    //계층형 게시판 상세페이지
    @GetMapping("/boardDetail/{boardNo}")
    public String hierarchicalBoardDetail(Model model
                                    , Principal principal
                                    , @PathVariable long boardNo
                                    , @ModelAttribute("comment")Comment comment
                                    , Criteria criteria
                                    , HttpSession session) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardDetail");

        log.info("boardNo : " + boardNo);

        if(principal != null)
            session.setAttribute("userId", principal.getName());

        model.addAttribute("boardDetail", hierarchicalBoardRepository.findByBoardNo(boardNo));
        model.addAttribute("comment", commentRepository.hierarchicalCommentList(boardNo
                                                        , PageRequest.of(criteria.getPageNum() - 1
                                                                , criteria.getAmount()
                                                                , Sort.by("commentGroupNo").ascending()
                                                                                .and(Sort.by("commentUpperNo").ascending()))));

        /*if(principal == null)
            log.info("Principal is null");
        else if(principal != null)
            log.info("Principal is not null " + principal.getName());*/

        log.info("getPageNum : " + criteria.getPageNum());
        log.info("getAmount : " + criteria.getAmount());

        log.info("comment : " + commentRepository.hierarchicalCommentList(boardNo
                , PageRequest.of(criteria.getPageNum() - 1
                        , criteria.getAmount()
                        , Sort.by("commentGroupNo").descending()
                                .and(Sort.by("commentUpperNo").ascending()))));

        int total = commentRepository.countBoardComment(boardNo);

        model.addAttribute("pageMaker", new PageDTO(criteria, total));



        return "th/board/boardDetail";
    }

    //계층형 게시판 수정페이지
    @GetMapping("/boardModify/{boardNo}")
    public String hierarchicalBoardModify(Model model, @PathVariable long boardNo) {
        /**
         * boardNo 받아서 처리
         */

        model.addAttribute("boardModify", hierarchicalBoardRepository.findByBoardNo(boardNo));

        log.info("boardModify");

        return "th/board/boardModify";
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
