package com.board.project.repository;

import com.board.project.domain.ImageData;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageDataRepositoryTest {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Test
    void imageDataTest(){


        List<ImageData> imageData = imageDataRepository.imageDataList(10L);

        System.out.println("list size : " + imageData.size());
        imageData.forEach(s -> System.out.println(s));
    }

}