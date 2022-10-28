package com.board.project.controller;

import com.board.project.repository.ImageBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/imageBoard")
public class ImageBoardController {

    @Autowired
    private ImageBoardRepository repository;


    //이미지 게시판 리스트(메인)
    @GetMapping("/imageBoardList")
    public String imageBoardMain(HttpServletRequest request, Model model){

        String filePath = request.getSession().getServletContext().getRealPath("img/");

        log.info("filepath : " + filePath);

        return "th/imageBoard/imageBoardList";

    }

    //이미지 게시판 상세
    @GetMapping("/imageBoardDetail")
    public String imageBoardDetail(Model model){
        /**
         * boardNo 받아서 처리
         */

        log.info("imageDetail");


//        model.addAttribute("detail", repository.imageDetail(10L));

        model.addAttribute("detail", repository.imageDetailDTO(10L));


        return "th/imageBoard/imageBoardDetail";
    }

    //이미지 게시판 작성
    @GetMapping("/imageBoardInsert")
    public void imageBoardInsert(){
        log.info("image insert");
    }

    //이미지 게시판 수정
    @GetMapping("/imageBoardModify")
    public void imageBoardModify(Model model){
        /**
         * boardNo 받아서 처리
         */

        log.info("image modify");
    }

}
