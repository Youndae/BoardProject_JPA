package com.board.project.service;

import com.board.project.domain.Member;
import com.board.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public int memberJoinProc(Member member) {
        if(member.getUserId() == null || member.getUserPw().length() == 0 || member.getUserName() == null)
            return 0;
        else
            return joinMember(member);
    }

    private int joinMember(Member member){
        try{
            member.setUserPw(passwordEncoder.encode(member.getUserPw()));
            memberRepository.save(member);
            log.info("join success");
            return 1;
        }catch (Exception e){
            log.info("join fail");
            return 0;
        }
    }
}
