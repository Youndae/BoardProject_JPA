package com.board.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

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

}