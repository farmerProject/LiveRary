package kr.hh.liverary.controller;

import kr.hh.liverary.domain.exception.ExceptionMessage;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.InvalidParameterException;
import kr.hh.liverary.domain.exception.document.NoSuchDocumentException;
import kr.hh.liverary.domain.exception.document.TitleDuplicatedException;
import kr.hh.liverary.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionHandlerController {

    private LogService logger;
    @Autowired
    ExceptionHandlerController(LogService logger) {
        this.logger = logger;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = ExceptionMessage.DUPLICATED_TITLE)
    @ExceptionHandler(TitleDuplicatedException.class)
    public void documentTitleConflicted(TitleDuplicatedException e) {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionMessage.NO_ITEM_IN_DB)
    @ExceptionHandler(RequestedItemIsNotFoundException.class)
    public void itemNotFounded() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ExceptionMessage.THE_PARAMETER_IS_INVALID)
    @ExceptionHandler(InvalidParameterException.class)
    public void noDoumentParameter() {

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionMessage.NO_SUCH_DOCUMENT)
    @ExceptionHandler(NoSuchDocumentException.class)
    public void noDoumentInDB() {

    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ExceptionMessage.UNEXPECTED_ERROR)
    @ExceptionHandler(Exception.class)
    public void unexpectedError(Exception e) {
        logger.error("Unexpected error: " + e.getMessage());
        logger.error(getStackString(e));
    }

    private String getStackString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
