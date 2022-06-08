package kr.hh.liverary.dto;

import kr.hh.liverary.domain.content.Content;
import kr.hh.liverary.domain.document.Document;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@RequiredArgsConstructor
public class ContentRequestDto {
    private Long id;
    private String content;
    private String writer;
    private Document document;

    @Builder
    public ContentRequestDto(@Nullable Long id, String content, String writer, Document document) {
        this.content = content;
        this.writer = writer;
        this.document = document;
    }

    public Content toEntity() {
        return Content.builder()
                .content(content)
                .writer(writer)
                .document(document)
        .build();
    }
}
