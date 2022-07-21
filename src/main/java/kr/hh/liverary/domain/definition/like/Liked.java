package kr.hh.liverary.domain.definition.like;

import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DEFINITION_ID")
    private Definition definition;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column
    private boolean isLiked;

    @Builder
    public Liked(String writer, Definition definition, boolean isLiked) {
        this.definition = definition;
        this.writer = writer;
        this.isLiked = isLiked;
    }
}
