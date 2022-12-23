package com.board.project.service;

import com.board.project.domain.Comment;
import com.board.project.domain.HierarchicalBoard;
import com.board.project.domain.ImageBoard;
import com.board.project.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * return 1 == success
 *
 * commentInsert 로직
 * insert와 replyInsert 모두 1차적으로 save를 진행해 commentNo를 먼저 받고
 * 그 뒤에 commentNo값이 필요한 commentGroupNo, commentUpperNo를 수정.
 * 이 때 save 할때는 userId, commentContent, commentDate등 NotNull 필드만 저장하도록 하고
 * 어느 게시판인지에 따라 게시판, 이미지 게시판 필드에 값이 들어갈수도 안들어갈 수도 있기 때문에 그 조건처리를 진행.
 * 그리고나서 GroupNo, UpperNo, Indent, 위 조건에 따른 게시판의 No를 update.
 *
 * 두번째 쿼리도 save로 처리할까 했지만 save로 처리하기 위해서는 1차 save로 이미 들어간 데이터들까지 모두 다시 담아서 처리해야한다는 점이 걸림.
 * 이게 더 효율적인지는 아직 잘 모르겠지만 insert냐 replyInsert냐에 따른 값 처리도 달라지고 어느 게시판이냐에 따른 처리도 구분해야 하는데
 * 이게 다 끝나고 새로 빌드해서 save할 때 이미 저장된 데이터들까지 다시 넣어 save를 날리는것은 불필요한 처리가 많다고 생각해서 이렇게 처리.
 */

@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PrincipalService principalService;

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public int commentInsert(Map<String, Object> commentData, Comment comment, Principal principal) throws Exception {

        //1. save
        long commentNo = saveAndGetCommentNo(commentData, principal);

        //2. update
        commentData.put("commentGroupNo", commentNo);
        commentData.put("commentIndent", 1);
        commentData.put("commentNo", commentNo);
        commentData.put("commentUpperNo", commentNo);

        checkBoard(commentData);

        return 1;

    }

    @Override
    public int commentReplyInsert(Map<String, Object> commentData, Comment comment, Principal principal) {

        try{

            long commentNo = saveAndGetCommentNo(commentData, principal);

            commentData.put("commentIndent", Integer.parseInt(commentData.get("commentIndent").toString()) + 1);
            commentData.put("commentUpperNo", commentData.get("commentUpperNo") + "," + commentNo);
            commentData.put("commentNo", commentNo);

            checkBoard(commentData);

            return 1;
        }catch (Exception e){
            log.info("save fail");
            return 2;
        }

    }

    long saveAndGetCommentNo(Map<String, Object> commentData, Principal principal) throws Exception {
        return commentRepository.save(Comment.builder()
                                            .member(principalService.checkPrincipal(principal))
                                            .commentContent(commentData.get("commentContent").toString())
                                            .commentDate(Date.valueOf(LocalDate.now()))
                                            .build())
                                .getCommentNo();
    }

    void checkBoard(Map<String, Object> commentData) throws Exception {
        log.info("checkBoard");

        if(commentData.get("boardNo") == null){
            modifyImageComment(commentData);
        }else if(commentData.get("boardNo") != null){
            modifyHierarchicalComment(commentData);
        }

    }

    void modifyImageComment(Map<String, Object> commentData) throws Exception{
        log.info("modifyImageBoardComment");

        commentRepository.modifyImageComment(Long.parseLong(commentData.get("commentGroupNo").toString())
        , Integer.parseInt(commentData.get("commentIndent").toString())
        , commentData.get("commentUpperNo").toString()
        , Long.parseLong(commentData.get("imageNo").toString())
        , Long.parseLong(commentData.get("commentNo").toString()));
    }

    void modifyHierarchicalComment(Map<String, Object> commentData) throws Exception{
        log.info("modify HierarchicalBoardComment");

        commentRepository.modifyHierarchicalComment(Long.parseLong(commentData.get("commentGroupNo").toString())
                , Integer.parseInt(commentData.get("commentIndent").toString())
                , commentData.get("commentUpperNo").toString()
                , Long.parseLong(commentData.get("boardNo").toString())
                , Long.parseLong(commentData.get("commentNo").toString()));

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
