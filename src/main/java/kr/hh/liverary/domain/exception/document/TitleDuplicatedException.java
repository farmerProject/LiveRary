package kr.hh.liverary.domain.exception.document;

import kr.hh.liverary.domain.HttpStatusCode;
import kr.hh.liverary.domain.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The document title is already in the use.")
public class TitleDuplicatedException extends CustomException {
    private static final long serialVersionUID = 8109988679741649986L;

    public TitleDuplicatedException() {
        super(HttpStatusCode.CONFLICT);
    }
}
