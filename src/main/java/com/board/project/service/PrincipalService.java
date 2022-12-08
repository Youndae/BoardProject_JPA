package com.board.project.service;


import com.board.project.domain.Member;

import java.security.Principal;

public interface PrincipalService {
    public Member checkPrincipal(Principal principal) throws Exception;
}
