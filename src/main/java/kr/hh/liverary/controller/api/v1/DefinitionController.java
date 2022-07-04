package kr.hh.liverary.controller.api.v1;

import kr.hh.liverary.domain.ApiResultItem;
import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.dto.ContentRequestDto;
import kr.hh.liverary.service.DefinitionService;
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
public class DefinitionController {
    private DefinitionService service;

    @Autowired
    public DefinitionController(DefinitionService definitionService) {
        this.service = definitionService;
    }

    @PostMapping("/v1/definitions")
    public ResponseEntity createDefinition(@RequestBody ContentRequestDto dto) throws Exception {
        ApiResultItem result = null;
        Map<String, Long> data = new HashMap<String, Long>();

        Long id = service.create(dto);
        data.put("id", id);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.CREATED.getCode())
                .message(HttpStatusCode.CREATED.toString())
                .data(data)
                .build();

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

//    @PostMapping("/v1/documents-collection")
//    public ResponseEntity createDefinitionWithDocument( @RequestBody ContentRequestDto contentDto) throws Exception {
//        ApiResultItem result = null;
//
//        Long id = service.createContentWithDocument(contentDto);
//        result = ApiResultItem.builder()
//                .code(HttpStatusCode.CREATED.getCode())
//                .message(HttpStatusCode.CREATED.toString())
//                .data(id)
//        .build();
//
////        return result;
//        return new ResponseEntity(result, HttpStatus.CREATED);
//    }

    @PutMapping("/v1/definitions/{id}")
    public ResponseEntity modify(@RequestBody ContentRequestDto dto, @PathVariable Long id) throws Exception {
        ApiResultItem result = null;
        Map<String, Long> data = new HashMap<String, Long>();

        Long contentId = service.modify(id, dto);
        data.put("id", contentId);
        result = ApiResultItem.builder()
                .code(HttpStatusCode.CREATED.getCode())
                .message(HttpStatusCode.CREATED.toString())
                .data(data)
                .build();

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping("/v1/documents/{title}/definitions")
    public ResponseEntity findByDocument(@PathVariable String title) throws Exception {
        ApiResultItem result = null;
        Map<String, Object> data = new HashMap<String, Object>();

        List<Object> contentList = service.findByDocumentTitle(title);
        data.put("size", contentList.size());
        data.put("items", contentList);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.OK.getCode())
                .message(HttpStatusCode.OK.toString())
                .data(data)
                .build();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/v1/definitions/latest/50")
    public ResponseEntity findTop50ByOrderByIdDesc() throws Exception {
        // 50개
        ApiResultItem result = null;
        Map<String, Object> data = new HashMap<String, Object>();

        List<Definition> contentList = service.findTop50ByOrderByIdDesc();
        data.put("size", contentList.size());
        data.put("items", contentList);

        result = ApiResultItem.builder()
                .code(HttpStatusCode.OK.getCode())
                .message(HttpStatusCode.OK.toString())
                .data(data)
                .build();
        return new ResponseEntity(result, HttpStatus.OK);

    }
}
