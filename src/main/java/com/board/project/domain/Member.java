package com.board.project.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {

    @Id
    private String userId;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<HierarchicalBoard> hierarchicalBoards;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private final Set<ImageBoard> imageBoards = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private final Set<Comment> comments = new HashSet<>();

    private String userPw;

    private String userName;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Auth> auths;



}
