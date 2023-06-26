package com.board.project.service;

import com.board.project.domain.entity.Member;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PrincipalServiceImpl implements PrincipalService{

    @Override
    public Member checkPrincipal(Principal principal) throws Exception {

        try{
            Member member = new Member();
            member.setUserId(principal.getName());
            return member;
        }catch (Exception e){
            return null;
        }

    }
}
