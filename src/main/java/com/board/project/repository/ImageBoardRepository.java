package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import com.board.project.domain.ImageDTO;
import com.board.project.domain.ImageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

    @Query(value = "SELECT new com.board.project.domain.ImageDTO(b.imageNo, d.imageName, b.imageContent, b.imageDate, b.imageTitle, b.member.userId, d.imageStep, d.oldName)" +
                    " FROM ImageBoard  b inner join ImageData d on b.imageNo = d.imageBoard.imageNo " +
                    "GROUP BY b.imageNo ORDER BY b.imageNo desc")
    List<ImageDTO> imageBoardList();


    @Query(value = "select new com.board.project.domain.ImageDTO(b.imageNo, d.imageName, b.imageContent, b.imageDate, b.imageTitle, b.member.userId, d.imageStep, d.oldName)" +
                    " from ImageBoard b inner join ImageData d on b.imageNo = d.imageBoard.imageNo where b.imageNo = ?1 " +
                    "order by d.imageStep asc")
    List<ImageDTO> imageDetailDTO(long imageNo);


    @Query(value = "SELECT b FROM ImageBoard b WHERE b.imageNo = ?1")
    ImageBoard modifyImageDetail(long imageNo);


    @Modifying
    @Transactional
    @Query(value = "UPDATE ImageBoard b SET b.imageTitle = ?1, b.imageContent = ?2, b.imageDataSet = ?3 WHERE b.imageNo = ?4")
    void imageBoardModify(String imageTitle, String imageContent, Set<ImageData> imageDataSet, long imageNo);
}
