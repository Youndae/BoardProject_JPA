package com.board.project.repository;

import com.board.project.domain.entity.ImageData;
import com.board.project.domain.dto.ImageDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {

    @Query(value = "SELECT d.imageName AS imageName" +
            ", d.imageBoard.imageNo AS imageNo" +
            ", d.oldName AS oldName" +
            ", d.imageStep AS imageStep " +
            "FROM ImageData d " +
            "WHERE d.imageBoard.imageNo = ?1 " +
            "ORDER BY d.imageStep asc")
    List<ImageDataDTO> imageDataList(long imageNo);

    @Query(value = "SELECT imageName " +
            "FROM imageData " +
            "WHERE imageNo = ?1",
    nativeQuery = true)
    List<String> deleteImageDataList(long imageNo);

    @Query(value = "SELECT MAX(d.imageStep) " +
            "FROM ImageData d " +
            "WHERE d.imageBoard.imageNo = ?1")
    int countImageStep(long imageNo);
}
