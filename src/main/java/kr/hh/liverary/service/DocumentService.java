package kr.hh.liverary.service;

import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.domain.document.DocumentRepository;
import kr.hh.liverary.dto.DocumentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository repo;

    @Transactional
    public String create(DocumentRequestDto dto) throws Exception {
        return repo.save(dto.toEntity()).getTitle();
    }

    @Transactional
    public String modify(String title, DocumentRequestDto dto) throws Exception {
        Document document = repo.findByTitle(title);
        if(document == null) throw new IllegalArgumentException("No data..");

        document.update(dto.getTitle(), dto.getWriter());

        return document.getTitle();
    }
}
