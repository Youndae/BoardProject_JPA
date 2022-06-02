package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageBoardRepositoryTest {

    @Autowired
    private ImageBoardRepository repository;

    @Test
    @Transactional
    void imageBoardTest(){
        repository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    void imageBoardMainTest(){
        int page = 0;
        int amount = 20;

        if(page > 0)
            page -= 1;

//        Page<ImageBoard> imageBoards = repository.imageBoardList(PageRequest.of(page, amount, Sort.by("imageNo").descending()));

    }

    @Test
    @Transactional
    void imageBoardDetailTest(){
        repository.findById(1L);
    }


}