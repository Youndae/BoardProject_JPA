package com.board.project.domain.dto;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageBoardDTO {

    private long imageNo;

    private String imageTitle;

    private String userId;

    private Date imageDate;

    private String imageContent;

    private String imageName;
}
