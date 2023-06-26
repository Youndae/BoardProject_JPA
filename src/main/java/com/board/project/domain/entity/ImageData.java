package com.board.project.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ImageData {

    @Id
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "imageNo")
    @ToString.Exclude
    private ImageBoard imageBoard;

    @NonNull
    private String oldName;

    private int imageStep;

    public void setImageBoard(ImageBoard imageBoard) {
        this.imageBoard = imageBoard;
    }
}
