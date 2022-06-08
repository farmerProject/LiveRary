package kr.hh.liverary.domain.content;

import kr.hh.liverary.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    public List<Object> findByDocument(Document document);
}
