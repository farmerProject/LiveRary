package kr.hh.liverary.common.interfaces;

public interface CrudInterface {

    public interface CreateTestInterface {
        public void success() throws Exception;
    }

    public interface ModifyTestInterface {
        public void success() throws Exception;
    }

    public interface FindTestInterface {
        public void findAll() throws Exception;
    }

    public interface RemoveTestInterface {
        public void removeAll() throws Exception;
    }


    public void test_create() throws Exception;
    public void test_modify() throws Exception;
    public void test_findAll() throws Exception;
    public void test_remove() throws Exception;
}
