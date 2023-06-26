package com.board.project.domain.entity;

import com.board.project.domain.entity.Auth;
import com.board.project.domain.entity.Comment;
import com.board.project.domain.entity.HierarchicalBoard;
import com.board.project.domain.entity.ImageBoard;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@ToString
@EqualsAndHashCode
@Getter
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

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
