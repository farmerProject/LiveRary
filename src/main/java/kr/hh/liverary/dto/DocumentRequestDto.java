package kr.hh.liverary.dto;

import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@RequiredArgsConstructor
public class DocumentRequestDto {
    private Long id;
    private String title;
    private String writer;

    @Builder
    public DocumentRequestDto(String title, String writer, @Nullable Long id) {
        this.title = title;
        this.writer = writer;
    }

    public Document toEntity() {
        return Document.builder()
                .title(title)
                .writer(writer)
        .build();
    }
}
