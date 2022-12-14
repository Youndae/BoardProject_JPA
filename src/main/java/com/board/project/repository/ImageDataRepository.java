package com.board.project.repository;

import com.board.project.domain.ImageData;
import com.board.project.domain.ImageDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {

    @Query(value = "SELECT d.imageName AS imageName" +
            ", d.imageBoard.imageNo AS imageNo" +
            ", d.oldName AS oldName" +
            ", d.imageStep AS imageStep FROM ImageData d WHERE d.imageBoard.imageNo = ?1 ORDER BY d.imageStep asc")
    List<ImageDataDTO> imageDataList(long imageNo);

}
