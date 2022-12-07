package com.board.project.repository;

import com.board.project.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT commentNo, userId, commentDate, commentContent, commentGroupNo, commentIndent, commentUpperNo, boardNo, imageNo from comment WHERE boardNo = :boardNo"
            , countQuery = "SELECT count(*) FROM comment WHERE boardNo = :boardNo"
            , nativeQuery = true)
    Page<Comment> hierarchicalCommentList(@Param("boardNo") long boardNo, Pageable pageable);

    @Query(value = "SELECT * from comment WHERE imageNo = :boardNo"
            , countQuery = "SELECT count(*) FROM comment WHERE imageNo = :boardNo"
            , nativeQuery = true)
    Page<Comment> imageCommentList(@Param("boardNo") String boardNo, Pageable pageable);
}
