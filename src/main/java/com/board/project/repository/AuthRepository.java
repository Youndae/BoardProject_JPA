package com.board.project.repository;

import com.board.project.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    @Query(value = "SELECT * " +
            "FROM auth " +
            "WHERE userId = 'coco'"
        , nativeQuery = true)
    List<Auth> findByUserId(String userId);
}
