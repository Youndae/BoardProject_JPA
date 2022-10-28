package com.board.project.security.domain;

import com.board.project.domain.Member;
import com.board.project.domain.MemberAuthDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class CustomUser extends User {

    private Member member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }


    /*public CustomUser(Member member){
        super(member.getUserId(), member.getUserPw(), member.getAuths().stream().map(auth ->
                new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

        log.info("custom user ");
        log.info("info : " + member.getUserId() + ", authList : " + member.getAuths());

        this.member = member;
    }*/


    public CustomUser(Member member){
        super(member.getUserId(), member.getUserPw(), member.getAuths().stream().map(auth ->
                new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));

        log.info("custom user ");
        log.info("info : " + member.getUserId() + ", authList : " + member.getAuths());

        this.member = member;
    }
}