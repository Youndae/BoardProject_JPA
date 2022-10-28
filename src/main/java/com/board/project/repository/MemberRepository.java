package com.board.project.repository;


import com.board.project.domain.Member;
import com.board.project.domain.MemberAuthDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    /*@Query(value = "SELECT m.userId, m.userPw, m.auths FROM Member m WHERE m.userId = ?1")
    Member getUserInfo(String userId);*/

    /*@Query(value = "SELECT new com.board.project.domain.MemberAuthDTO(m.userId, m.userPw, m.auth) FROM Member m WHERE m.userId = ?1")
    MemberAuthDTO getUser(String userId);*/


    @Query(value = "SELECT m.userId, m.userPw, m.auths FROM Member m WHERE m.userId = ?1")
    Member userInfo(String userId);

    /*@Query(value = "select new com.board.project.domain.MemberAuthDTO(m.userId, m.userPw, m.auths) from Member m where m.userId = ?1")
    MemberAuthDTO userInfo2(String userId);*/


    Member findByUserId(String userId);

}
