package kr.hh.liverary.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WarnLogger {
    public void logging(String message) {
        log.warn(message);
    }
}
