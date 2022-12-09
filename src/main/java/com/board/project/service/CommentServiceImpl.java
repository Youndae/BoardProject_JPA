package com.board.project.service;

import com.board.project.domain.Comment;
import com.board.project.domain.HierarchicalBoard;
import com.board.project.domain.ImageBoard;
import com.board.project.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
/**
 * return 1 == success
 * return 2 == insert error
 */

@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public int commentInsert(Map<String, Object> commentData, Comment comment) throws Exception {

        try{
            comment = checkBoard(commentData, comment);
            long maxNo = commentRepository.maxCommentNo();
            comment.setCommentGroupNo(maxNo);
            comment.setCommentUpperNo(String.valueOf(maxNo));


            commentRepository.save(comment);

            return 1;
        }catch (Exception e){
            log.info("save fail");
            e.printStackTrace();
            return 2;
        }

    }

    @Override
    public int commentReplyInsert(Map<String, Object> commentData, Comment comment) throws Exception {

        try{
            comment = checkBoard(commentData, comment);
            comment.setCommentGroupNo(Long.parseLong(commentData.get("commentGroupNo").toString()));
            comment.setCommentIndent(Integer.parseInt(commentData.get("commentIndent").toString()) + 1);
            long maxNo = commentRepository.maxCommentNo();
            comment.setCommentUpperNo(commentData.get("commentUpperNo").toString() + "," + maxNo);

            commentRepository.save(comment);

            return 1;
        }catch (Exception e){
            log.info("save fail");
            e.printStackTrace();
            return 2;
        }

    }

    static Comment checkBoard(Map<String, Object> commentData, Comment comment){

        if(commentData.get("boardNo") == null){
            ImageBoard imageBoard = new ImageBoard();
            imageBoard.setImageNo(Long.parseLong(commentData.get("imageNo").toString()));
            comment.setImageBoard(imageBoard);
        }else{
            HierarchicalBoard hierarchicalBoard = new HierarchicalBoard();
            hierarchicalBoard.setBoardNo(Long.parseLong(commentData.get("boardNo").toString()));
            comment.setHierarchicalBoard(hierarchicalBoard);
        }

        comment.setCommentContent(commentData.get("commentContent").toString());
        comment.setCommentDate(Date.valueOf(LocalDate.now()));

        return comment;
    }

    @Override
    public int commentDelete(Comment comment) throws Exception {

        try{
            commentRepository.deleteById(comment.getCommentNo());
            return 1;
        }catch (Exception e){
            return 0;
        }

    }
}
