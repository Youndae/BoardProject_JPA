package com.board.project.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class ImageBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    @NonNull
    private String imageTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    private Date imageDate;

    private String imageContent;

    @OneToMany(mappedBy = "imageBoard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ImageData> imageDataSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageBoard that = (ImageBoard) o;
        return Objects.equals(imageNo, that.imageNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageNo);
    }

    public void addImageData(ImageData imageData){
        imageDataSet.add(imageData);
        imageData.setImageBoard(this);
    }
}
