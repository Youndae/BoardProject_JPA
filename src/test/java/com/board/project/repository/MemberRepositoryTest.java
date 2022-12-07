package com.board.project.repository;

import com.board.project.domain.Member;
import com.board.project.domain.MemberAuthDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @Test
    @Transactional
    void memberTest(){
        Optional<Member> mem = memberRepository.findById("coco");

        System.out.println(mem.map(memb ->
                memb.getHierarchicalBoards()));
    }


    @Test
    @Transactional
    void loginTest(){
//        System.out.println(memberRepository.findById("coco"));

        Member member = memberRepository.findByUserId("coco");
//        MemberAuthDTO member = memberRepository.userInfo2("coco");

//        Member member = memberRepository.userInfo("coco");

        System.out.println("-------------------");
        System.out.println("member is : " + member);

//        member.getAuths().forEach(auth -> System.out.println(auth));



//        System.out.println(memberRepository.userInfo("coco"));
    }

}