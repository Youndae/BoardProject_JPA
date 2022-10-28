package com.board.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AuthRepositoryTest {

    @Autowired
    private AuthRepository repository;

    @Test
    void authTest(){
//        System.out.println(repository.findByUserId("coco"));
    }


}