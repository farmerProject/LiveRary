package kr.hh.liverary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResultItem {
    private HttpStatusMessage message;
    private int code;
    private Object data;

    @Builder
    public ApiResultItem(HttpStatusMessage message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }
}

