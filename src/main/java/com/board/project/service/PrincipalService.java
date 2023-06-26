package com.board.project.service;


import com.board.project.domain.entity.Member;

import java.security.Principal;

public interface PrincipalService {
    Member checkPrincipal(Principal principal) throws Exception;
}
