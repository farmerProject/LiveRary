package kr.hh.liverary.domain.exception;

import kr.hh.liverary.domain.HttpStatusCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -607989445890743569L;

    private HttpStatusCode statusCode;

    public CustomException(HttpStatusCode statusCode) {
        super(statusCode.name());
        this.statusCode = statusCode;
    }
}
