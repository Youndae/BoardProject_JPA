package com.board.project.controller;

import com.board.project.domain.dto.BoardCommentDTO;
import com.board.project.domain.dto.ImageBoardDTO;
import com.board.project.domain.dto.ImageBoardModifyDTO;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.dto.PageDTO;
import com.board.project.repository.CommentRepository;
import com.board.project.repository.ImageBoardRepository;
import com.board.project.service.ImageBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/imageBoard")
@RequiredArgsConstructor
public class ImageBoardController {


    private final ImageBoardRepository imageBoardRepository;

    private final CommentRepository commentRepository;

    private final ImageBoardService imageBoardService;


    //이미지 게시판 리스트(메인)
    @GetMapping("/imageBoardList")
    public String imageBoardMain(Model model, Criteria cri){


        Page<ImageBoardDTO> dto = imageBoardService.getImageList(cri);

        model.addAttribute("imageList", dto.getContent());
        model.addAttribute("pageMaker", new PageDTO(cri, dto.getTotalPages()));

        return "th/imageBoard/imageBoardList";

    }

    //이미지 게시판 상세
    @GetMapping("/imageBoardDetail/{imageNo}")
    public String imageBoardDetail(@PathVariable long imageNo
                                    , Model model
                                    , Criteria criteria
                                    , Principal principal
                                    , HttpSession session){
        /**
         * boardNo 받아서 처리
         */

        log.info("imageDetail");

        log.info("imageNo : " + imageNo);

        if(principal != null)
            session.setAttribute("userId", principal.getName());

        model.addAttribute("detail", imageBoardRepository.imageDetailDTO(imageNo));

        Page<BoardCommentDTO> commentDTO = commentRepository.imageCommentList(imageNo
                                            , PageRequest.of(criteria.getPageNum() - 1
                                                    , criteria.getImageAmount()
                                                    , Sort.by("commentGroupNo").ascending()
                                                            .and(Sort.by("commentUpperNo").descending())));

        model.addAttribute("comment", commentDTO.getContent());

        model.addAttribute("pageMaker", new PageDTO(criteria, commentDTO.getTotalPages()));

        return "th/imageBoard/imageBoardDetail";
    }

    //이미지 게시판 작성
    @GetMapping("/imageBoardInsert")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String imageBoardInsert(){
        log.info("image insert");

        return "th/imageBoard/imageBoardInsert";
    }

    //이미지 게시판 수정
    @GetMapping("/imageBoardModify/{imageNo}")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public String imageBoardModify(Model model, @PathVariable long imageNo, Principal principal){
        /**
         * boardNo 받아서 처리
         */

        log.info("image modify");

        log.info("imageNo : " + imageNo);

        ImageBoardModifyDTO dto = imageBoardService.getImageModifyData(imageNo, principal);

        if(dto == null){
            return "th/error/error";
        }else {
            model.addAttribute("list", dto);

            return "th/imageBoard/imageBoardModify";
        }
    }

}
