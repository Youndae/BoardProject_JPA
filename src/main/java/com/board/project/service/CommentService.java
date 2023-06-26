package com.board.project.service;

import com.board.project.domain.entity.Comment;

import java.security.Principal;
import java.util.Map;

public interface CommentService {

    int commentInsert(Map<String, Object> commentData, Comment comment, Principal principal) throws Exception;

    int commentReplyInsert(Map<String, Object> commentData, Comment comment, Principal principal) throws Exception;

    int commentDelete(Comment comment) throws Exception;
}
