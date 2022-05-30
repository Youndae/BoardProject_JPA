package com.board.project.repository;

import com.board.project.domain.ImageBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

}
