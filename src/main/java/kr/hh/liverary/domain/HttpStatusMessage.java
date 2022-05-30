package kr.hh.liverary.domain;

public enum HttpStatusMessage {
    OK("OK"),
    CREATED("CREATED"),
    NO_CONTENT("NO_CONTENT"),
    BAD_REQUEST("BAD_REQUEST"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    NOT_FOUND("NOT_FOUND"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE"),
    DB_ERROR("DB_ERROR");

    private final String msg;
    HttpStatusMessage(String msg) {
        this.msg = msg;
    }
}
