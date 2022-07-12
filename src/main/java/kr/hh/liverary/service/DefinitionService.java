package kr.hh.liverary.service;

import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.DefinitionRepository;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.NoDocumentParameterException;
import kr.hh.liverary.domain.exception.document.NoSuchDocumentException;
import kr.hh.liverary.dto.ContentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefinitionService {

    private DefinitionRepository repo;
    private DocumentService documentService;

    @Autowired
    public DefinitionService(DefinitionRepository repo, DocumentService documentService) {
        this.repo = repo;
        this.documentService = documentService;
    }

    @Transactional
    public Long create(ContentRequestDto dto) throws Exception {
        if(dto.getDocument() == null) throw new NoDocumentParameterException();
        Document document = documentService.findByTitle(dto.getDocument().getTitle());

        if(document == null) throw new NoSuchDocumentException();
        Definition definition = Definition.builder()
                .content(dto.getContent())
                .writer(dto.getWriter())
                .document(document)
                .build();
        Long savedItemId = storeItem(definition).getId();
        return savedItemId;
    }

    private Definition storeItem(Definition content) throws Exception {
        return repo.save(content);
    }

    @Transactional
    public Long modify(Long contentId, ContentRequestDto contentDto) throws Exception {
        Definition targetContent = repo.findById(contentId)
                .orElseThrow(() -> new RequestedItemIsNotFoundException());
        Definition modified = targetContent.update(contentDto.getWriter(), contentDto.getContent());
        return modified.getId();
    }

    @Transactional
    public int updateLikes(Long contentId, boolean liked) throws Exception {
        Definition targetDefinition = repo.findById(contentId)
                .orElseThrow(() -> new RequestedItemIsNotFoundException());
        int changedLikes = liked ?  1 : -1;
        Definition updated = targetDefinition.updateLikes(changedLikes);
        return updated.getLikes();
    }

    @Transactional
    public List<Object> findByDocumentTitle(String title) throws Exception {
        Document document = documentService.findByTitle(title);
        if(document == null) throw new NoSuchDocumentException();
        return repo.findByDocument(document);
    }

    @Transactional
    public List<Definition> findTop50ByOrderByIdDesc() throws Exception{
        return repo.findTop50ByOrderByIdDesc();
    }
}
