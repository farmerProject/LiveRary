package kr.hh.liverary.log;

public interface LogInterface {
    public void info(String message);
    public void warn(String message);
    public void error(String message);
    public void trace(String message);
    public void debug(String message);
}
