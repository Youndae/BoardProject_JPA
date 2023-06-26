package com.board.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HierarchicalBoardModifyDTO {

    private long boardNo;

    private String boardTitle;

    private String boardContent;
}
