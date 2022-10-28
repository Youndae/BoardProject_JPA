package com.board.project.repository;

import com.board.project.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    @Query(nativeQuery = true, value = "select * from auth where userId = 'coco'")
    List<Auth> findByUserId(String userId);
}
