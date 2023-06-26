package com.board.project.domain.dto;

import com.board.project.domain.entity.Criteria;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

    private int startPage;

    private int endPage;

    private boolean prev, next;

    private Criteria cri;

    public PageDTO(Criteria cri, int totalPages){
        this.cri = cri;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 5.0) * 5);
        this.startPage = this.endPage - 4;

        int realEnd = totalPages;

        if(realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
