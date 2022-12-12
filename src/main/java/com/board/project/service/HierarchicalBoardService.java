package com.board.project.service;

import com.board.project.domain.HierarchicalBoard;

import javax.servlet.http.HttpServletRequest;

public interface HierarchicalBoardService {

    void insertBoard(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception;

    void insertBoardReply(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception;

    void deleteBoard(HierarchicalBoard hierarchicalBoard);
}
