package kr.hh.liverary.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    public Document findByTitle(String title);
}
