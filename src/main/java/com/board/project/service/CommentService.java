package com.board.project.service;

import com.board.project.domain.Comment;

import java.security.Principal;
import java.util.Map;

public interface CommentService {

    int commentInsert(Map<String, Object> commentData, Comment comment) throws Exception;

    int commentReplyInsert(Map<String, Object> commentData, Comment comment) throws Exception;

    int commentDelete(Comment comment) throws Exception;
}
