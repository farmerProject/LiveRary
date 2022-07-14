package kr.hh.liverary.common.definition;

import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.DefinitionRepository;
import kr.hh.liverary.domain.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinitionAction {
    @Autowired
    private DefinitionRepository repo;

    public Definition storeItem(String writer, String content, Document document) {
        return repo.save(Definition.builder()
                .writer(writer)
                .content(content)
                .document(document)
                .build()
        );
    }
}
