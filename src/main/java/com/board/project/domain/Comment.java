package com.board.project.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;

    private Date commentDate;

    private String commentContent;

    private Long commentGroupNo;

    private int commentIndent;

    private String commentUpperNo;

    @ManyToOne
    @JoinColumn(name = "imageNo")
    private ImageBoard imageBoard;

    @ManyToOne
    @JoinColumn(name = "boardNo")
    private HierarchicalBoard hierarchicalBoard;

}
