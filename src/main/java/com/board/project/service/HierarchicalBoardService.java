package com.board.project.service;

import com.board.project.domain.dto.HierarchicalBoardDTO;
import com.board.project.domain.dto.HierarchicalBoardModifyDTO;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.entity.HierarchicalBoard;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface HierarchicalBoardService {

    Page<HierarchicalBoardDTO> getHierarchicalBoardList(Criteria cri);

    HierarchicalBoardModifyDTO getModifyData(long boardNo, Principal principal);

    long insertBoard(HttpServletRequest request, Principal principal) throws Exception;

    void insertBoardReply(HttpServletRequest request, Principal principal) throws Exception;

    long deleteBoard(long boardNo, Principal principal);

    long boardModify(HttpServletRequest request);
}
