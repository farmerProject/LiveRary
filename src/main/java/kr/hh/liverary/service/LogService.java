package kr.hh.liverary.service;

import kr.hh.liverary.log.ErrorLogger;
import kr.hh.liverary.log.WarnLogger;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private WarnLogger warn = new WarnLogger();
    private ErrorLogger error = new ErrorLogger();

    public void warn(String message) {
        warn.logging(message);
    }

    public void error(String message) {
        error.logging(message);
    }
}
