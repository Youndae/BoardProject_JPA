package com.board.project.domain.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Criteria {

    private int pageNum;
    private int boardAmount;
    private int imageAmount;
    private String keyword;
    private String searchType;

    public Criteria(){
        this(1, 20, 15);
    }

    public Criteria(int pageNum, int boardAmount, int imageAmount) {
        this.pageNum = pageNum;
        this.boardAmount = boardAmount;
        this.imageAmount = imageAmount;
    }
}
