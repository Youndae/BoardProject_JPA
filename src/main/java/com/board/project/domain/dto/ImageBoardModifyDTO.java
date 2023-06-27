package com.board.project.domain.dto;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageBoardModifyDTO {

    private long imageNo;

    private String imageTitle;

    private Date imageDate;

    private String imageContent;
}
