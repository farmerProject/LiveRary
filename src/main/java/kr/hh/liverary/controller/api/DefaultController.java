package kr.hh.liverary.controller.api;

import kr.hh.liverary.domain.ApiResultItem;
import kr.hh.liverary.domain.HttpStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/api")
@RestController
public class DefaultController {

    @GetMapping("/health")
    public ResponseEntity healthCheck() throws Exception {
        ApiResultItem result = null;
        Map<String, String> data = new HashMap<String, String>();

        data.put("status", "OK");

        result = ApiResultItem.builder()
                .code(HttpStatusCode.OK.getCode())
                .message(HttpStatusCode.OK.toString())
                .data(data)
        .build();

        return new ResponseEntity(result, HttpStatus.OK);

    }

}
