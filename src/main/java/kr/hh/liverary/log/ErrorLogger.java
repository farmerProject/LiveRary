package kr.hh.liverary.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorLogger {
    public void logging(String message) {
        log.error(message);
    }
}
