package kr.hh.liverary.domain.definition;

import kr.hh.liverary.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {
    public List<Object> findByDocument(Document document);
}
