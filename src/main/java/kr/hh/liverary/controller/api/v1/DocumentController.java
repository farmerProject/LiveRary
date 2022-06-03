package kr.hh.liverary.controller.api.v1;

import kr.hh.liverary.domain.ApiResultItem;
import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.dto.DocumentRequestDto;
import kr.hh.liverary.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class DocumentController {
    private final DocumentService service;

    @PostMapping("/v1/documents")
    public ApiResultItem create(@RequestBody DocumentRequestDto dto) throws Exception{
        ApiResultItem result = null;

        String title = service.create(dto);
        result = ApiResultItem.builder()
                .code(HttpStatusCode.CREATED.getCode())
                .message(HttpStatusCode.CREATED.toString())
                .data(title)
        .build();

        return result;
    }

    @PutMapping("/v1/documents/{title}")
    public ApiResultItem modify(@PathVariable String title, @RequestBody DocumentRequestDto dto) throws Exception{
        ApiResultItem result = null;

            String modifiedTitle = service.modify(title, dto);
            result = ApiResultItem.builder()
                    .code(HttpStatusCode.CREATED.getCode())
                    .message(HttpStatusCode.CREATED.toString())
                    .data(modifiedTitle)
            .build();

        return result;
    }
}
