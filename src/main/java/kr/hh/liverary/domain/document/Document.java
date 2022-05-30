package kr.hh.liverary.domain.document;

import kr.hh.liverary.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 20, nullable = false)
    private String writer;

    @Builder
    public Document(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

    public Document update(String title, String writer) {
        this.title = title;
        this.writer = writer;
        return this;
    }

}
