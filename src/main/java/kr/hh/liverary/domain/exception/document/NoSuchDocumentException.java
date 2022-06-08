package kr.hh.liverary.domain.exception.document;

import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchDocumentException extends CustomException {
    private static final long serialVersionUID = 3189018338710083369L;

    public NoSuchDocumentException() {
        super(HttpStatusCode.NOT_FOUND);
    }
}
