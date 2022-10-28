package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageDTO;
import com.board.project.domain.ImageData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        Page<ImageBoard> imageBoards = repository.imageBoardList(PageRequest.of(page, amount, Sort.by("imageNo").descending()));

        System.out.println(imageBoards.getContent().get(2).getImageData().getClass().getName());

        // imageBoardMain 에서는 imageBoard 테이블 데이터와 ImageData 테이블에서 이미지 데이터 1개만 가져오면 됨.
        // 근데 이걸 그냥 페이징으로 처리한다고 하면 그렇게 처리가 안되고 다 가져오니까 차라리 nativeQuery로 하는게 나을지 고민.
        // 이것도 ImageDTO를 통해 처리.


    }

    @Test
    @Transactional
    void imageBoardDetailTest(){
//        Optional<ImageBoard> image = repository.findById(10L);


//        List<ImageBoard> image = repository.imageDetail(10L);


        List<ImageDTO> image = repository.imageDetailDTO(10L);
        System.out.println(image);

        //ImageBoard Entity에서 ImageData에 대해 Set으로 설정한 뒤에 findById를 하게 되면 테이블에 저장된 순서대로 출력되지 않지만
        //List로 설정하게 되면 테이블에 저장된 순서대로 출력된다.
        //여기서 문제점은 테이블에 저장된 순서가 아닌 step 컬럼을 기준으로 정렬되어 나와야 하니 findById는 사용하기 어렵다고 봐야함.
        //그리고 또 하나 알게된점. List 상태에서 fetchType을 EAGER로 설정한 뒤에 테스트를 실행하면 오류가 발생한다.
        //LAZY로 바꾸면 정상적으로 처리된다.
        //오류는 PersistenceException과 MultipleBagFetchException이 발생한다.
        //Set에서는 LAZY랑 EAGER 모두 문제없이 동작.


    }


}