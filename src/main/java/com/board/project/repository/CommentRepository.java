package com.board.project.repository;

import com.board.project.domain.dto.BoardCommentDTO;
import com.board.project.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT new com.board.project.domain.dto.BoardCommentDTO(" +
            "c.commentNo" +
            ", c.member.userId" +
            ", c.commentDate" +
            ", c.commentContent" +
            ", c.commentGroupNo" +
            ", c.commentIndent" +
            ", c.commentUpperNo) " +
            "FROM Comment c " +
            "WHERE c.hierarchicalBoard.boardNo = :boardNo"
    , countQuery = "SELECT count(c) " +
            "FROM Comment c " +
            "WHERE c.hierarchicalBoard.boardNo = :boardNo")
    Page<BoardCommentDTO> hierarchicalCommentList(@Param("boardNo")long boardNo, Pageable pageable);

    @Query(value = "SELECT new com.board.project.domain.dto.BoardCommentDTO(" +
            "c.commentNo" +
            ", c.member.userId" +
            ", c.commentDate" +
            ", c.commentContent" +
            ", c.commentGroupNo" +
            ", c.commentIndent" +
            ", c.commentUpperNo) " +
            "FROM Comment c " +
            "WHERE c.imageBoard.imageNo = :imageNo"
    , countQuery = "SELECT count(c) " +
            "FROM Comment c " +
            "WHERE c.imageBoard.imageNo = :imageNo")
    Page<BoardCommentDTO> imageCommentList(@Param("imageNo")long boardNo, Pageable pageable);


    @Query(value = "SELECT ifnull(max(commentNo) + 1, 1) " +
            "FROM comment",
        nativeQuery = true)
    long maxCommentNo();

    @Modifying
    @Transactional
    @Query(value = "UPDATE comment " +
            "SET commentGroupNo = ?1" +
            ", commentIndent = ?2" +
            ", commentUpperNo = ?3" +
            ", imageNo = ?4 " +
            "WHERE commentNo = ?5"
    , nativeQuery = true)
    int modifyImageComment(long commentGroupNo, int commentIndent, String commentUpperNo, long imageNo, long commentNo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE comment " +
            "SET commentGroupNo = ?1" +
            ", commentIndent = ?2" +
            ", commentUpperNo = ?3" +
            ", boardNo = ?4 " +
            "WHERE commentNo = ?5"
    , nativeQuery = true)
    int modifyHierarchicalComment(long commentGroupNo, int commentIndent, String commentUpperNo, long boardNo, long commentNo);



}
