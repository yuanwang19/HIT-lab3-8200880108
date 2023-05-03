package otherDirectory;

import centralObject.CentralObject;
import org.junit.Test;
import physicalObject.PhysicalObject;

import static org.junit.Assert.*;

public class RelationTest {
    /**
     * getObject1()
     * 策略：
     * 测试关系中源点的返回值
     * 按源点的类型划分：中心物体、轨道物体
     */
    @Test
    public void testGetObject1() {
        CentralObject center = new CentralObject("center");
        PhysicalObject obj1 = new PhysicalObject("obj1");
        PhysicalObject obj2 = new PhysicalObject("obj2");
        Relation<CentralObject, PhysicalObject> relation1 = new Relation<>(center, obj1, 20);
        Relation<PhysicalObject, PhysicalObject> relation2 = new Relation<>(obj1, obj2, 10);

        // 验证源点类型为中心物体
        assertEquals(center, relation1.getObject1());
        // 验证源点类型为轨道物体
        assertEquals(obj1, relation2.getObject1());

    }

    /**
     * Method: getObject2()
     * 测试关系中终点的返回值
     */
    @Test
    public void testGetObject2() {
        PhysicalObject physicalObject1 = new PhysicalObject("obj1");
        PhysicalObject physicalObject2 = new PhysicalObject("obj2");
        CentralObject centralObject = new CentralObject("center");
        Relation<CentralObject, PhysicalObject> relation1 = new Relation<>(centralObject, physicalObject1, 20);
        Relation<PhysicalObject, PhysicalObject> relation2 = new Relation<>(physicalObject1, physicalObject2, 10);

// Test the destination objects of the two relations
// When the source object is a central object
        assertEquals(physicalObject1, relation1.getObject2());
// When the source object is a physical object on a track
        assertEquals(physicalObject2, relation2.getObject2());
    }

    /**
     * Method: getWeight()
     * 测试关系权重的返回值，权重必须大于零
     */
    @Test
    public void testGetWeight() {
        PhysicalObject physicalObject = new PhysicalObject("obj");
        CentralObject centralObject = new CentralObject("center");

        Relation<CentralObject, PhysicalObject> relation1 = new Relation<>(centralObject, physicalObject, 30);

        assertEquals(30, relation1.getWeight(), 0.0);
    }

    /**
     * Method: equals(Object obj)
     * 按是否相同划分：相同、不同
     * 按不同点划分：源点、终点、权重
     */
    @Test
    public void testEquals() {
        PhysicalObject physicalObject1 = new PhysicalObject("obj1");
        PhysicalObject physicalObject2 = new PhysicalObject("obj2");
        PhysicalObject physicalObject3 = new PhysicalObject("obj3");
        CentralObject centralObject = new CentralObject("center");
        Relation<CentralObject, PhysicalObject> relation1 = new Relation<>(centralObject, physicalObject1, 20);
        Relation<CentralObject, PhysicalObject> relation2 = new Relation<>(centralObject, physicalObject2, 20);
        Relation<PhysicalObject, PhysicalObject> relation3 = new Relation<>(physicalObject1, physicalObject2, 10);
        Relation<PhysicalObject, PhysicalObject> relation4 = new Relation<>(physicalObject1, physicalObject2, 20);
        Relation<PhysicalObject, PhysicalObject> relation5 = new Relation<>(physicalObject1, physicalObject2, 20);
        Relation<PhysicalObject, PhysicalObject> relation6 = new Relation<>(physicalObject2, physicalObject3, 20);
        Relation<PhysicalObject, PhysicalObject> relation7 = new Relation<>(physicalObject1, physicalObject3, 20);

//两对关系相同
        if(relation5.equals(relation4)){
            System.out.println("两对关系相同");
        }

//两对关系不同
        if(!relation1.equals(relation2)){
            System.out.println("终点不同");
        }
        if(!relation6.equals(relation7)){
            System.out.println("源点不同");
        }
        if(!relation3.equals(relation4)){
            System.out.println("权重不同");
        }
        if(!relation5.equals(relation6)){
            System.out.println("一般情况");
        }
    }

}
