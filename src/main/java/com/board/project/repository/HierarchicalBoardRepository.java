package com.board.project.repository;

import com.board.project.domain.dto.HierarchicalBoardDTO;
import com.board.project.domain.dto.HierarchicalBoardModifyDTO;
import com.board.project.domain.entity.HierarchicalBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface HierarchicalBoardRepository extends JpaRepository<HierarchicalBoard, Long> {

    //HierarchicalBoard default List
    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.member.userId" +
            ", b.boardContent" +
            ", b.boardDate" +
            ", b.boardGroupNo" +
            ", b.boardIndent" +
            ", b.boardUpperNo) " +
            "FROM HierarchicalBoard b"
    , countQuery = "SELECT c.contentCount " +
            "FROM CountTable c " +
            "WHERE c.boardName = 'hierarchicalboard'")
    Page<HierarchicalBoardDTO> hierarchicalBoardList(Pageable pageable);


    //HierarchicalBoard SearchTitle List
    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.member.userId" +
            ", b.boardContent" +
            ", b.boardDate" +
            ", b.boardGroupNo" +
            ", b.boardIndent" +
            ", b.boardUpperNo) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword"
    , countQuery = "SELECT count(b) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword")
    Page<HierarchicalBoardDTO> hierarchicalBoardListSearchTitle(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard SearchContent List
    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.member.userId" +
            ", b.boardContent" +
            ", b.boardDate" +
            ", b.boardGroupNo" +
            ", b.boardIndent" +
            ", b.boardUpperNo) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword"
    , countQuery = "SELECT count(b) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword")
    Page<HierarchicalBoardDTO> hierarchicalBoardListSearchContent(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard SearchUser List
    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.member.userId" +
            ", b.boardContent" +
            ", b.boardDate" +
            ", b.boardGroupNo" +
            ", b.boardIndent" +
            ", b.boardUpperNo) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.member.userId LIKE :keyword"
    , countQuery = "SELECT count(b) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.member.userId LIKE :keyword")
    Page<HierarchicalBoardDTO> hierarchicalBoardListSearchUser(@Param("keyword") String keyword, Pageable pageable);

    //HierarchicalBoard Search Title + Content List
    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.member.userId" +
            ", b.boardContent" +
            ", b.boardDate" +
            ", b.boardGroupNo" +
            ", b.boardIndent" +
            ", b.boardUpperNo) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword " +
            "OR b.boardContent LIKE :keyword"
    , countQuery = "SELECT count(b) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardTitle LIKE :keyword " +
            "OR b.boardContent LIKE :keyword")
    Page<HierarchicalBoardDTO> hierarchicalBoardListSearchTitleOrContent(@Param("keyword") String keyword, Pageable pageable);


    HierarchicalBoard findByBoardNo(long boardNo);

    @Query(value = "SELECT new com.board.project.domain.dto.HierarchicalBoardModifyDTO(" +
            "b.boardNo" +
            ", b.boardTitle" +
            ", b.boardContent) " +
            "FROM HierarchicalBoard b " +
            "WHERE b.boardNo = ?1")
    HierarchicalBoardModifyDTO getModifyData(long boardNo);


    @Query(value = "SELECT userId " +
            "FROM hierarchicalBoard " +
            "WHERE boardNo = ?1"
    , nativeQuery = true)
    String checkWriter(long boardNo);


    @Modifying
    @Transactional
    @Query(value = "UPDATE hierarchicalBoard " +
            "SET boardTitle = :boardTitle" +
            ", boardContent = :boardContent " +
            "WHERE boardNo = :boardNo"
    , nativeQuery = true)
    void boardModify(@Param("boardTitle") String boardTitle, @Param("boardContent") String boardContent, @Param("boardNo") long boardNo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE hierarchicalBoard " +
            "SET boardContent = ?1" +
            ", boardIndent = ?2" +
            ", boardGroupNo = ?3" +
            ", boardUpperNo = ?4 " +
            "WHERE boardNo = ?5"
    , nativeQuery = true)
    void boardInsertModify(String boardContent, int boardIndent, long boardGroupNo, String boardUpperNo, long boardNo);
}
