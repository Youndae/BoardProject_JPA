package com.board.project.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Member member;

    private Date commentDate;

    private String commentContent;

    private Long commentGroupNo;

    private int commentIndent;

    private String commentUpperNo;

    @ManyToOne
    @JoinColumn(name = "imageNo")
    @ToString.Exclude
    private ImageBoard imageBoard;

    @ManyToOne
    @JoinColumn(name = "boardNo")
    @ToString.Exclude
    private HierarchicalBoard hierarchicalBoard;



}
