package kr.hh.liverary.controller.api.v1;

import kr.hh.liverary.domain.ApiResultItem;
import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.document.Document;
import kr.hh.liverary.dto.DocumentRequestDto;
import kr.hh.liverary.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class DocumentController {
    private DocumentService service;

    @Autowired
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping("/v1/documents")
    public ResponseEntity create(@RequestBody DocumentRequestDto dto) throws Exception{
        ApiResultItem result = null;
        Map<String, String> data = new HashMap<String, String>();

        String title = service.getTitleFromCreatedItem(dto);
        data.put("title", title);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.CREATED.getCode())
                .message(HttpStatusCode.CREATED.toString())
                .data(data)
                .build();

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping("/v1/documents/{title}")
    public ResponseEntity modify(@PathVariable String title, @RequestBody DocumentRequestDto dto) throws Exception{
        ApiResultItem result = null;
        Map<String, String> data = new HashMap<String, String>();

        String modifiedTitle = service.modify(title, dto);
        data.put("title", modifiedTitle);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.CREATED.getCode())
                .message(HttpStatusCode.CREATED.toString())
                .data(data)
                .build();

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping("/v1/documents/search")
    public ResponseEntity search(@RequestParam String keyword) {
        ApiResultItem result = null;
        Map<String, Object> data = new HashMap<>();
        List<Document> items = service.findByTitleContaining(keyword);
        data.put("size", items.size());
        data.put("datas", items);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.OK.getCode())
                .message(HttpStatusCode.OK.toString())
                .data(data)
                .build();

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
