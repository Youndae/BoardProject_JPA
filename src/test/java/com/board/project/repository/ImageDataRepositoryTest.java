package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageData;
import com.board.project.domain.Member;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageDataRepositoryTest {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Test
    void imageDataTest(){


        /*List<ImageData> imageData = imageDataRepository.imageDataList(10L);

        System.out.println("list size : " + imageData.size());
        imageData.forEach(s -> System.out.println(s));*/

        System.out.println(new ResponseEntity<>(imageDataRepository.imageDataList(10L), HttpStatus.OK));
    }

    @Test
    @Transactional
    void exceptionTest() {
        Member.builder().userId("coco").build();
        try{
            System.out.println("ExceptionTest");


            imageBoardRepository.save(
                    ImageBoard.builder()
                    .imageTitle("testInsert")
                    .member(Member.builder().userId("coco").build())
                    .imageContent("testInsert Content")
                    .imageDate(Date.valueOf(LocalDate.now()))
                    .build()
            );

            insertImageData();
        }catch (Exception e){
            System.out.println("exception!");
        }
    }

    void insertImageData() throws Exception{

        System.out.println("insert ImageData");

        ImageData imageData = new ImageData();

//        imageData.setImageName("testImageData");

        System.out.println("oldName : " + imageData.getOldName());
        imageDataRepository.saveAndFlush(
          imageData
        );
        System.out.println("insert ImageData Success");

    }

    @Test
    void imageNameTest(){
        List<String> deleteImageDataList = imageDataRepository.deleteImageDataList(10L);

        deleteImageDataList.forEach(s -> System.out.println(s));
    }

    @Test
    void imageStepCountTest(){
        int step = imageDataRepository.countImageStep(10L);

        System.out.println("step : " + step);
    }

}