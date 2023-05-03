package otherDirectory;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommonObjectTest {
    /**
     * getName()
     */
    @Test
    public void testGetName() {
        CommonObject Object = new CommonObject("object");
        assertEquals("object", Object.getName());
    }

    /**
     * equals(Object obj)
     */
    @Test
    public void testEquals() {
        // 创建 CommonObject 对象
        CommonObject obj1 = new CommonObject("test");
        CommonObject obj2 = new CommonObject("test");
        CommonObject obj3 = new CommonObject("another");

        // 测试相同对象的情况
        assertTrue(obj1.equals(obj1));

        // 测试相等对象的情况
        assertTrue(obj1.equals(obj2));
        assertTrue(obj2.equals(obj1));

        // 测试不相等对象的情况
        assertFalse(obj1.equals(obj3));
        assertFalse(obj2.equals(obj3));
        assertFalse(obj3.equals(obj1));

        // 测试与非 CommonObject 类型对象的比较
        assertFalse(obj1.equals("test"));
        assertFalse(obj1.equals(null));
    }
    /**
     * toString()
     */
    @Test
    public void testToString() {
        CommonObject commonObject = new CommonObject("object");
        assertEquals("object", commonObject.toString());
    }

    /**
     * compareTo(CommonObject that)
     */
    @Test
    public void testCompareTo() {
        CommonObject commonObject1 = new CommonObject("obj1");
        // 创建 CommonObject 对象
        CommonObject obj1 = new CommonObject("apple");
        CommonObject obj2 = new CommonObject("banana");
        CommonObject obj3 = new CommonObject("banana");

        // 测试相同对象的情况
        assertEquals(0, obj2.compareTo(obj3));

        // 测试不同对象的情况
        assertTrue(obj1.compareTo(obj2) < 0);
        assertTrue(obj2.compareTo(obj1) > 0);

        // 测试与自身比较的情况
        assertEquals(0, obj2.compareTo(obj2));
    }


}