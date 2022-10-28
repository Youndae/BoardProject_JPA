package com.board.project.repository;

import com.board.project.domain.Auth;
import com.board.project.domain.HierarchicalBoard;
import com.board.project.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


   /* @Test
    void pagingTest(){
        //컨트롤러에서 받을 값
        int page = 0;
        int amount = 20;

        if(page > 0)
            page -= 1;

        String keyword = "test";

        keyword = "%" + keyword + "%";

        Page<HierarchicalBoard> boardData = repository.hierarchicalBoardListSearchTitle(keyword, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));

        List<HierarchicalBoard> boards = boardData.getContent();
        System.out.println(boardData.getNumberOfElements());
        System.out.println("size : " + boards.size());
        System.out.println(boards);
        System.out.println();
        System.out.println("all Data count : " + boardData.getTotalElements());
        System.out.println("This page number : " + boardData.getNumber());
        System.out.println("all Page Count : " + boardData.getTotalPages());

    }*/

    @Test
    void pagingSearchTest(){
        int page = 0;
        int amount = 20;
        String searchType = "userId";

        String keyword = "coco";
        keyword = "%" + keyword + "%";

        if(page > 0)
            page -= 1;

        if(searchType == "title" || searchType == null){
            repository.hierarchicalBoardListSearchTitle(keyword, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));
        }else if(searchType == "content"){
            repository.hierarchicalBoardListSearchContent(keyword, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));
        }else if(searchType == "userId"){
            repository.hierarchicalBoardListSearchUser(keyword, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));
        }else if(searchType == "titleAndContent"){
            repository.hierarchicalBoardListSearchTitleOrContent(keyword, PageRequest.of(page, amount, Sort.by("boardGroupNo").descending().and(Sort.by("boardUpperNo").ascending())));
        }

    }

    @Test
    @Transactional
    void boardDetailTest(){
        Long boardNo = 1L;

        System.out.println(repository.findById(boardNo));

        Optional<HierarchicalBoard> hierarchicalBoard = Optional.ofNullable(HierarchicalBoard.builder()
                .boardNo(1L)
                .boardTitle("test board1")
                .boardContent("test board Content1")
                .boardDate(Date.valueOf(LocalDate.of(2022, 05, 28)))
                .boardIndent(0)
                .boardGroupNo(1L)
                .boardUpperNo("1")
//                .member(new Member("coco", "1234", "코코"))
                .build());

        assertEquals(hierarchicalBoard, repository.findById(boardNo));

    }

}