package com.board.project.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageNo;

    private String imageTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private Member member;

    private Date imageDate;

    private String imageContent;

    @OneToMany(mappedBy = "imageBoard")
    @ToString.Exclude
    private final Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "imageBoard")
    private final Set<ImageData> imageData = new HashSet<>();

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
}
