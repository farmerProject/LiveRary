package kr.hh.liverary.controller;

import kr.hh.liverary.domain.exception.ExceptionMessage;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.NoDocumentParameterException;
import kr.hh.liverary.domain.exception.document.NoSuchDocumentException;
import kr.hh.liverary.domain.exception.document.TitleDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = ExceptionMessage.DUPLICATED_TITLE)
    @ExceptionHandler(TitleDuplicatedException.class)
    public void documentTitleConflicted(TitleDuplicatedException e) {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionMessage.NO_ITEM_IN_DB)
    @ExceptionHandler(RequestedItemIsNotFoundException.class)
    public void itemNotFounded() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ExceptionMessage.NO_DOCUMENT_PARAMETER)
    @ExceptionHandler(NoDocumentParameterException.class)
    public void noDoumentParameter() {

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ExceptionMessage.NO_SUCH_DOCUMENT)
    @ExceptionHandler(NoSuchDocumentException.class)
    public void noDoumentInDB() {

    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ExceptionMessage.UNEXPECTED_ERROR)
    @ExceptionHandler(Exception.class)
    public void unexpectedError() {
        System.out.println("This is unexpected error");
    }
}
