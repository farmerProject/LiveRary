package kr.hh.liverary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResultItem {
    private int code;
    private Object data;
    private String message;

    @Builder
    public ApiResultItem(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}

