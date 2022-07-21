package kr.hh.liverary.dto;

import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.like.Liked;
import kr.hh.liverary.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikedRequestDto {
    private Long id;
    private Definition definition;
    private String writer;
    private boolean isLiked;

    @Builder
    public LikedRequestDto(Definition definition, String writer, boolean isLiked) {
        this.definition = definition;
        this.writer = writer;
        this.isLiked = isLiked;
    }

    public Liked toEntity() {
        return Liked.builder()
                .definition(definition)
                .writer(writer)
                .isLiked(isLiked)
        .build();
    }
}
