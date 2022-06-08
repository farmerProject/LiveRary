package kr.hh.liverary.common.interfaces;

public interface CrudInterface {

    public interface CreateTestInterface {
        public void success() throws Exception;
    }

    public interface ModifyTestInterface {
        public void success() throws Exception;
    }

    public interface InquiryTestInterface {
        public void findAll() throws Exception;
    }

    public interface RemoveTestInterface {
        public void removeAll() throws Exception;
    }
}
