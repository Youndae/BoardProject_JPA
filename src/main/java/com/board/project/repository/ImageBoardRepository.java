package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageDTO;
import com.board.project.domain.ImageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

    @Query(value = "SELECT b FROM ImageBoard b")
    Page<ImageBoard> imageBoardList(Pageable pageable);

    @Query(value = "select b, d from ImageBoard b inner join ImageData d ON b.imageNo = d.imageBoard.imageNo where b.imageNo = ?1 order by d.imageStep asc")
    List<ImageBoard> imageDetail(long imageNo);


    @Query(value = "select new com.board.project.domain.ImageDTO(b.imageNo, d.imageName, b.imageContent, b.imageDate, b.imageTitle, b.member.userId, d.imageStep, d.oldName)" +
                    " from ImageBoard b inner join ImageData d on b.imageNo = d.imageBoard.imageNo where b.imageNo = ?1 " +
                    "order by d.imageStep asc")
    List<ImageDTO> imageDetailDTO(long imageNo);

}
