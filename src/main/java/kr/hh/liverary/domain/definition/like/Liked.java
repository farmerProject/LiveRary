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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private boolean isLiked;

    @Builder
    public Liked(User user, Definition definition, boolean isLiked) {
        this.definition = definition;
        this.user = user;
        this.isLiked = isLiked;
    }
}
