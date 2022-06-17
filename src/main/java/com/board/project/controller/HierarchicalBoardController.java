package com.board.project.controller;

import com.board.project.domain.Criteria;
import com.board.project.repository.HierarchicalBoardRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/board")
public class HierarchicalBoardController {

    @Autowired
    private HierarchicalBoardRepository repository;


    //계층형 게시판 리스트(메인)
    @GetMapping("/boardList")
    public String hierarchicalBoardMain(Model model, Criteria cri) {

        /*if (cri.getKeyword() == null || cri.getKeyword() == "") {// default List
            model.addAttribute("boardList",
                    repository.hierarchicalBoardList(
                            PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "t") {//title 검색시 사용
            model.addAttribute("boardList",
                    repository.hierarchicalBoardListSearchTitle(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "c") {//content 검색시 사용
            model.addAttribute("boardList",
                    repository.hierarchicalBoardListSearchContent(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getSearchType() == "u") {// user 검색 시 사용
            model.addAttribute("boardList",
                    repository.hierarchicalBoardListSearchUser(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        } else if (cri.getKeyword() == "tc") {// title and content 검색시 사용
            model.addAttribute("boardList",
                    repository.hierarchicalBoardListSearchTitleOrContent(
                            cri.getKeyword()
                            , PageRequest.of(cri.getPageNum()
                                    , cri.getAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending()))));
        }*/


        log.info("boardList log");

        return "th/board/boardList";
    }

    //계층형 게시판 상세페이지
    @GetMapping("/boardDetail")
    public void hierarchicalBoardDetail(Model model) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardDetail");
    }

    //계층형 게시판 수정페이지
    @GetMapping("/boardModify")
    public void hierarchicalBoardModify(Model model) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardModify");
    }

    //계층형 게시판 글작성
    @GetMapping("/boardInsert")
    public void hierarchicalBoardInsert() {
        log.info("boardInsert");
    }

    //계층형 게시판 답글 작성
    @GetMapping("/boardReply")
    public void hierarchicalBoardReply(Model model) {
        /**
         * boardNo 받아서 처리
         */
        log.info("boardReply");
    }

}
