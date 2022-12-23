package com.board.project.service;

import com.board.project.domain.HierarchicalBoard;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface HierarchicalBoardService {

    void insertBoard(HttpServletRequest request, Principal principal) throws Exception;

    void insertBoardReply(HttpServletRequest request, Principal principal) throws Exception;

    void deleteBoard(HierarchicalBoard hierarchicalBoard);
}
