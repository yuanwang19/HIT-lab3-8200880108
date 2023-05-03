package centralObject;

import org.junit.Test;

import static org.junit.Assert.*;

public class CentralObjectTest {


    /**
     * getInstance(String centralName)
     * 测试轨道物体姓名的返回值
     */
    @Test
    public void testGetInstance() {
        CentralObject centralObject = CentralObject.getInstance("centerobject");
        assertEquals("centerobject", centralObject.getName());
    }
}
