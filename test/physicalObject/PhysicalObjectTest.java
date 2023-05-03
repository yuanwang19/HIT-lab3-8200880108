package physicalObject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhysicalObjectTest {


    /**
     * getInstance(String trackObjectName)
     * 测试轨道物体姓名的返回值
     */
    @Test
    public void testGetInstance() {
        PhysicalObject physicalObject = PhysicalObject.getInstance("object");
        assertEquals("object", physicalObject.getName());
    }
}
