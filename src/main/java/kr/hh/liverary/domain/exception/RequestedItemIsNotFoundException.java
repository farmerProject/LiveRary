package kr.hh.liverary.domain.exception;

import kr.hh.liverary.domain.HttpStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested item is not found in database.")
public class RequestedItemIsNotFoundException extends CustomException{

    private static final long serialVersionUID = -4134441286982506252L;

    public RequestedItemIsNotFoundException() {
        super(HttpStatusCode.NOT_FOUND);
    }
}
