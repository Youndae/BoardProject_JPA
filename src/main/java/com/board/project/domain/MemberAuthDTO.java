package com.board.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthDTO {

    private String userId;

    private String userPw;

    private Set<Auth> auth;

}
