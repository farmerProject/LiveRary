package kr.hh.liverary.service;

import kr.hh.liverary.domain.content.Content;
import kr.hh.liverary.domain.content.ContentRepository;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.NoDocumentParameterException;
import kr.hh.liverary.domain.exception.document.NoSuchDocumentException;
import kr.hh.liverary.dto.ContentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ContentService {

    private final ContentRepository repo;
    private final DocumentService documentService;

    @Transactional
    public Long create(ContentRequestDto dto) throws Exception {
        if(dto.getDocument() == null) throw new NoDocumentParameterException();
        Document document = documentService.findByTitle(dto.getDocument().getTitle());

        if(document == null) throw new NoSuchDocumentException();
        Long savedItemId = storeItem(dto.toEntity()).getId();
        return savedItemId;
    }

//    @Transactional
//    public Long createContentWithDocument(ContentRequestDto contentDto) throws Exception {
//        Document document = contentDto.getDocument();
//        DocumentRequestDto documentDto = DocumentRequestDto.builder()
//                .title(document.getTitle())
//                .writer(document.getWriter())
//        .build();
//        documentService.create(documentDto);
//        ContentRequestDto contentWithDocument
//                = ContentRequestDto.builder()
//                                .document(document)
//                                .writer(contentDto.getWriter())
//                                .content(contentDto.getContent())
//                .build();
//        Long savedItemId = storeItem(contentWithDocument.toEntity()).getId();
//
//        return savedItemId;
//    }

    private Content storeItem(Content content) throws Exception {
        return repo.save(content);
    }

    @Transactional
    public Long modify(Long contentId, ContentRequestDto contentDto) throws Exception {
        Content targetContent = repo.findById(contentId)
                .orElseThrow(() -> new RequestedItemIsNotFoundException());
        Content modified = targetContent.update(contentDto.getWriter(), contentDto.getContent());
        return modified.getId();
    }

    @Transactional
    public List<Object> findByDocumentTitle(String title) throws Exception {
        Document document = documentService.findByTitle(title);
        if(document == null) throw new NoSuchDocumentException();
        return repo.findByDocument(document);
    }
}
