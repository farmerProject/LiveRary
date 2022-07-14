package kr.hh.liverary.common.document;

import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentAction {
    @Autowired
    private DocumentRepository repo;

    public Document storeItem(String title, String writer) {
        return repo.save(Document.builder()
                .title(title)
                .writer(writer)
                .build()
        );
    }
}
