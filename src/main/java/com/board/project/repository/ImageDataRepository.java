package com.board.project.repository;

import com.board.project.domain.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ImageDataRepository extends JpaRepository<ImageData, String> {

}
