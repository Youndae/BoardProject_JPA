package com.board.project.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class HierarchicalBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    private String boardTitle;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Member member;

    private String boardContent;

    private Date boardDate;

    private Long boardGroupNo;

    private int boardIndent;

    private String boardUpperNo;

    @OneToMany(mappedBy = "hierarchicalBoard")
    @ToString.Exclude
    private final Set<Comment> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HierarchicalBoard that = (HierarchicalBoard) o;
        return Objects.equals(boardNo, that.boardNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardNo);
    }
}
