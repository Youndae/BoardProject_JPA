package com.board.project.repository;

import com.board.project.domain.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {
}
