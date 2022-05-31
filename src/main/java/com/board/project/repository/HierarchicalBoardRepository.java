package com.board.project.repository;

import com.board.project.domain.HierarchicalBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HierarchicalBoardRepository extends JpaRepository<HierarchicalBoard, Long> {

    @Query(value = "select boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent from hierarchicalBoard WHERE boardTitle like ?1"
            , countQuery = "select count(*) from hierarchicalBoard where boardTitle like ?1"
            , nativeQuery = true)
    Page<HierarchicalBoard> hierarchicalBoardList(String keyword, String searchType, Pageable pageable);

    @Query(value = "select boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent from hierarchicalBoard order by boardGroupNo desc, boardUpperNo limit ?1, ?2", nativeQuery = true)
    List<HierarchicalBoard> hierarchicalBoardList2(int val1, int val2);

//    Page<HierarchicalBoard> findHierarchicalBoardOrderByBoardGroupNoDescAndOOrderByBoardUpperNoAsc(Pageable pageable);


}
