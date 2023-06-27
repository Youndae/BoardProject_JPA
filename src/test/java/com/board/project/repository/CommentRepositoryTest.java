package com.board.project.repository;

import com.board.project.domain.dto.BoardCommentDTO;
import com.board.project.domain.entity.Comment;
import com.board.project.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    /*@Test
    void commentTest(){
        repository.findAll().forEach(System.out::println);
    }*/

    /*@Test
    void boardCommentTest(){
        String boardNo = "1";
        String boardType = "image";
        int page = 0;
        int amount = 20;

        if(page > 0)
            page -= 1;

        if(boardType == "hierarchical")
            repository.hierarchicalCommentList(boardNo, PageRequest.of(page, amount, Sort.by("commentGroupNo").descending().and(Sort.by("commentUpperNo").ascending())));
        else if(boardType == "image")
            repository.imageCommentList(boardNo, PageRequest.of(page, amount, Sort.by("commentGroupNo").descending().and(Sort.by("commentUpperNo").ascending())));
    }*/

    @Test
    void commentTest(){
        long boardNo = 100018L;
        int pageNum = 0;
        int amount = 20;

        /*System.out.println(repository.commentTest(boardNo, PageRequest.of(pageNum, amount, Sort.by("commentGroupNo").ascending()
                .and(Sort.by("commentUpperNo").ascending()))));*/




//        repository.commentTest2(boardNo);

        Page<BoardCommentDTO> dto = commentRepository.hierarchicalCommentList(boardNo, PageRequest.of(pageNum, amount
                    ,Sort.by("commentGroupNo").ascending()
                        .and(Sort.by("commentUpperNo").ascending())));


        System.out.println(dto.getTotalPages());

    }



    @Test
    @Transactional
    void maxNoTest(){

        long maxNo = commentRepository.maxCommentNo();

        Member member = new Member();
        member.setUserId("coco");

        /*Comment comment = Comment.builder()
                .member(member)
                .commentNo(maxNo)
                .commentContent("testContent")
                .imageBoard(imageBoardRepository.modifyImageDetail(10L))
                .commentGroupNo(maxNo)
                .commentIndent(0)
                .commentUpperNo(String.valueOf(maxNo))
                .commentDate(Date.valueOf(LocalDate.now()))
                .build();

        commentRepository.save(comment);*/


    }


    @Test
    void getIdTest(){
        Member member = new Member();
        member.setUserId("coco");
        long commentNo = commentRepository.saveAndFlush(Comment.builder().member(member).commentContent("testContent")
                .commentDate(Date.valueOf(LocalDate.now())).commentGroupNo(50L).build()).getCommentNo();
/*
        System.out.println("-------------------------------------------------");

        System.out.println("commentNo : " + commentNo);

        Comment comment = Comment.builder()
                .commentNo(commentNo)
                .commentGroupNo(commentNo)
                .commentUpperNo(String.valueOf(commentNo))
                .commentIndent(0)
                .imageBoard(imageBoardRepository.modifyImageDetail(10L))
                .build();

        System.out.println("----------------------------------------------");

        commentRepository.save(comment);*/


    }


}