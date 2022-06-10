package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

    @Query(value = "SELECT b FROM ImageBoard b")
    Page<ImageBoard> imageBoardList(Pageable pageable);

}
