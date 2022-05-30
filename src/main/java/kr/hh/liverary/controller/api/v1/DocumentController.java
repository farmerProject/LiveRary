package kr.hh.liverary.controller.api.v1;

import kr.hh.liverary.domain.ApiResultItem;
import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.HttpStatusMessage;
import kr.hh.liverary.dto.DocumentRequestDto;
import kr.hh.liverary.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DocumentController {
    private final DocumentService service;

    @PostMapping("/api/v1/documents")
    public ApiResultItem create(@RequestBody DocumentRequestDto dto) {
        ApiResultItem result = null;

        try {
            String title = service.create(dto);
            result = ApiResultItem.builder()
                    .code(HttpStatusCode.CREATED.getCode())
                    .message(HttpStatusMessage.CREATED)
                    .data(title)
            .build();
        }catch (Exception e) {
            e.printStackTrace();
//            result = ApiResultItem
        }

        return result;
    }

    @PutMapping("/api/v1/documents/{title}")
    public ApiResultItem modify(@PathVariable String title, @RequestBody DocumentRequestDto dto) {
        ApiResultItem result = null;

        try {
            String modifiedTitle = service.modify(title, dto);
            result = ApiResultItem.builder()
                    .code(HttpStatusCode.CREATED.getCode())
                    .message(HttpStatusMessage.CREATED)
                    .data(modifiedTitle)
            .build();
        }catch (Exception e) {
            e.printStackTrace();
//            result = ApiResultItem
        }

        return result;
    }
}
