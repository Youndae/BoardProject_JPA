package com.board.project.repository;

import com.board.project.domain.HierarchicalBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HierarchicalBoardRepository extends JpaRepository<HierarchicalBoard, Long> {

    //HierarchicalBoard default List And SerchTitle List
    @Query(value = "SELECT boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent FROM hierarchicalBoard WHERE boardTitle LIKE :keyword"
            , countQuery = "SELECT count(*) FROM hierarchicalBoard WHERE boardTitle LIKE :keyword"
            , nativeQuery = true)
    Page<HierarchicalBoard> hierarchicalBoardListSearchTitle(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard SearchContent List
    @Query(value = "SELECT boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent FROM hierarchicalBoard WHERE boardContent LIKE :keyword"
            , countQuery = "SELECT count(*) FROM hierarchicalBoard WHERE boardContent LIKE :keyword"
            , nativeQuery = true)
    Page<HierarchicalBoard> hierarchicalBoardListSearchContent(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard SearchUser List
    @Query(value = "SELECT boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent FROM hierarchicalBoard WHERE userId LIKE :keyword"
            , countQuery = "SELECT count(*) FROM hierarchicalBoard WHERE userId LIKE :keyword"
            , nativeQuery = true)
    Page<HierarchicalBoard> hierarchicalBoardListSearchUser(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard Search Title + Content List
    @Query(value = "SELECT boardNo, concat(repeat('   ', boardIndent), '', boardTitle) AS boardTitle, userId, boardContent, boardDate, boardGroupNo, boardUpperNo, boardIndent FROM hierarchicalBoard WHERE boardTitle LIKE :keyword or boardContent LIKE :keyword"
            , countQuery = "SELECT count(*) FROM hierarchicalBoard WHERE boardTitle LIKE :keyword or boardContent LIKE :keyword"
            , nativeQuery = true)
    Page<HierarchicalBoard> hierarchicalBoardListSearchTitleOrContent(@Param("keyword") String keyword, Pageable pageable);


    @Query(value = "SELECT h FROM HierarchicalBoard h")
    Page<HierarchicalBoard> hierarchicalBoardList(Pageable pageable);


}
