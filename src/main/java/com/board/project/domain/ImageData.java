package com.board.project.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageData {

    @Id
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "imageNo")
    @ToString.Exclude
    private ImageBoard imageBoard;

    private String oldName;

    private int imageStep;

}
