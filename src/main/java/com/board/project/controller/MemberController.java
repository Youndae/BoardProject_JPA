package com.board.project.controller;

import com.board.project.domain.Member;
import com.board.project.repository.MemberRepository;
import com.board.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() throws Exception{
        return "th/member/join";
    }

    @GetMapping("/loginForm")
    public String login() throws Exception{
        return "th/member/loginForm";
    }

    @PostMapping("/joinProc")
    @ResponseBody
    public int joinProc(Member member) throws Exception{

        log.info("member : " + member);

        log.info("member userId : " + member.getUserId());
        log.info("member pw : " + member.getUserPw());
        log.info("member name : " + member.getUserName());


        return memberService.memberJoinProc(member);
        /*try{
            member.setUserPw(passwordEncoder.encode(member.getUserPw()));
            memberRepository.save(member);
            log.info("success");
            return 1;
        }catch (Exception e){
            log.info("fail");
            return 0;
        }*/

    }

    @PostMapping("/checkUserId")
    public int checkUserId(Member member) throws Exception{

        if(memberRepository.findByUserId(member.getUserId()) != null){
            return 1;
        }else{
            return 0;
        }
    }
}
