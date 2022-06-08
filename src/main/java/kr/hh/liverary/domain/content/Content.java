package kr.hh.liverary.domain.content;

import kr.hh.liverary.domain.document.Document;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Builder
    public Content(String writer, String content, Document document) {
        this.writer = writer;
        this.content = content;
        this.document = document;
    }

    public Content update(String writer, String content) {
        this.writer = writer;
        this.content = content;
        return this;
    }

}
