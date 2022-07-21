package kr.hh.liverary.domain.exception.document;

import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There is no such document.")
public class InvalidParameterException extends CustomException {

    private static final long serialVersionUID = -8186472244469365958L;

    public InvalidParameterException() {
        super(HttpStatusCode.BAD_REQUEST);
    }
}
