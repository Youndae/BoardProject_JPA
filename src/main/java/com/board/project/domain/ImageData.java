package com.board.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageData {

    @Id
    private String imageName;

    private Long imageNo;

    private String oldName;

    private int imageStep;

}
