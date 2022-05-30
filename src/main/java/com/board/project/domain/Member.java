package com.board.project.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    private String userId;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private Set<HierarchicalBoard> hierarchicalBoards = new HashSet<>();

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private Set<ImageBoard> imageBoards = new HashSet<>();

    private String userPw;

    private String userName;
}
