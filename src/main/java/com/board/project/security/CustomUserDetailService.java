package com.board.project.security;

import com.board.project.domain.Auth;
import com.board.project.domain.Member;
import com.board.project.domain.MemberAuthDTO;
import com.board.project.repository.AuthRepository;
import com.board.project.repository.MemberRepository;
import com.board.project.security.domain.CustomUser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Setter(onMethod_ = {@Autowired})
    private MemberRepository repository;

//    @Setter(onMethod = {@Autowired})
//    private AuthRepository authRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("CustomUserDetailService");

        Member member = repository.userInfo(username);

//        Member member = repository.findByUserId(username);


//        Member member = authRepository.findByUserId("coco");

//        List<Auth> mem = repository.findByUserId("coco");

//        Optional<Member> member = repository.findById(username);

//        log.info(String.valueOf(member));


//        Member member2 = repository.findById(username);

        log.info("member log is : " + String.valueOf(member));

        return member == null ? null : new CustomUser(member);
    }
}
