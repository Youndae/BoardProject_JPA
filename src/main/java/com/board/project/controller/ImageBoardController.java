package com.board.project.controller;

import com.board.project.domain.Criteria;
import com.board.project.domain.PageDTO;
import com.board.project.repository.CommentRepository;
import com.board.project.repository.ImageBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardController {

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private CommentRepository commentRepository;


    //이미지 게시판 리스트(메인)
    @GetMapping("/imageBoardList")
    public String imageBoardMain(HttpServletRequest request, Model model){

        String filePath = request.getSession().getServletContext().getRealPath("img/");

        log.info("filepath : " + filePath);

        model.addAttribute("imageList", imageBoardRepository.imageBoardList());

        return "th/imageBoard/imageBoardList";

    }

    //이미지 게시판 상세
    @GetMapping("/imageBoardDetail/{imageNo}")
    public String imageBoardDetail(@PathVariable long imageNo, Model model, Criteria criteria){
        /**
         * boardNo 받아서 처리
         */

        log.info("imageDetail");

        log.info("imageNo : " + imageNo);

//        model.addAttribute("detail", repository.imageDetail(10L));

        model.addAttribute("detail", imageBoardRepository.imageDetailDTO(imageNo));
        model.addAttribute("comment", commentRepository.imageCommentList(imageNo
                                                        , PageRequest.of(criteria.getPageNum() - 1
                                                                , criteria.getAmount()
                                                                , Sort.by("commentGroupNo").ascending()
                                                                            .and(Sort.by("commentUpperNo").descending()))));

        int total = commentRepository.countImageComment(imageNo);

        model.addAttribute("pageMaker", new PageDTO(criteria, total));


        return "th/imageBoard/imageBoardDetail";
    }

    //이미지 게시판 작성
    @GetMapping("/imageBoardInsert")
    public String imageBoardInsert(){
        log.info("image insert");

        return "th/imageBoard/imageBoardInsert";
    }

    //이미지 게시판 수정
    @GetMapping("/imageBoardModify")
    public String imageBoardModify(Model model){
        /**
         * boardNo 받아서 처리
         */

        log.info("image modify");

        return "th/imageBoard/imageBoardModify";
    }

}
