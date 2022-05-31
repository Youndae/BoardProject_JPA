package com.board.project.repository;

import com.board.project.domain.HierarchicalBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HierarchicalBoardRepositoryTest {

    @Autowired
    private HierarchicalBoardRepository repository;


    @Test
    void boardTest(){
        System.out.println(repository.findById(1L));
    }


    @Test
    void pagingTest(){
        //컨트롤러에서 받을 값
        int page = 1;
        int amount = 20;



        String keyword = "test%";
        String searchType = "boardTitle";

        Page<HierarchicalBoard> boardData = repository.hierarchicalBoardList(keyword, searchType, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));

        List<HierarchicalBoard> boards = boardData.getContent();
        System.out.println(boards);
        System.out.println();
        System.out.println("all Data count : " + boardData.getTotalElements());
        System.out.println("This page number : " + boardData.getNumber());
        System.out.println("all Page Count : " + boardData.getTotalPages());

    }

}