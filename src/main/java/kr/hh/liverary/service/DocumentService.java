package kr.hh.liverary.service;

import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.TitleDuplicatedException;
import kr.hh.liverary.dto.DocumentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository repo;

    public String getTitleFromCreatedItem(DocumentRequestDto dto) throws Exception {
        return create(dto).getTitle();
    }

    @Transactional
    public Document create(DocumentRequestDto dto) throws Exception {
        if(isItemPresence(dto.getTitle())) throw new TitleDuplicatedException();
        return storeItem(dto.toEntity());
    }


    @Transactional
    public String modify(String title, DocumentRequestDto dto) throws Exception {
        if(dto.getTitle() == title) throw new TitleDuplicatedException();

        Document document = findByTitle(title);
        if(document == null) throw new RequestedItemIsNotFoundException();
        else if(isItemPresence(dto.getTitle())) throw new TitleDuplicatedException();

        Document modified = document.update(dto.getTitle(), dto.getWriter());

        return modified.getTitle();
    }

    private boolean isItemPresence(String title) {
        Document document = findByTitle(title);
        if(document == null) return false;
        else return true;
    }

    private Document storeItem(Document document) throws Exception {
        return repo.save(document);
    }

    public Document findByTitle(String title) {
        return repo.findByTitle(title);
    }
}
