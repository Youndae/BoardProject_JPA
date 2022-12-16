package com.board.project.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    @NonNull
    private String imageTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private Member member;

    private Date imageDate;

    private String imageContent;

    @OneToMany(mappedBy = "imageBoard", fetch = FetchType.LAZY)
    @ToString.Exclude
    private final Set<Comment> comments = new HashSet<>();

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
