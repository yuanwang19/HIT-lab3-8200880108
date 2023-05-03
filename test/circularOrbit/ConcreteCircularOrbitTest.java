package circularOrbit;

import centralObject.CentralObject;
import org.junit.Test;
import otherDirectory.Difference;
import physicalObject.PhysicalObject;
import track.Track;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ConcreteCircularOrbitTest {

    CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<>();
    CentralObject center = new CentralObject("core");
    Track track1 = new Track("track1", 100);
    Track track2 = new Track("track2", 200);
    Track track3 = new Track("track3", 300);
    Track track4 = new Track("track4", 400);
    PhysicalObject object1 = new PhysicalObject("object1");
    PhysicalObject object2 = new PhysicalObject("object2");
    PhysicalObject object3 = new PhysicalObject("object3");
    PhysicalObject object4 = new PhysicalObject("object4");
    PhysicalObject object5 = new PhysicalObject("object5");
    PhysicalObject object6 = new PhysicalObject("object6");


    /**
     * setCentralObject(L centralObject)
     * 测试中心点是否设置成功
     */
    @Test
    public void testSetCentralObject() {
        circular.setCentralObject(center);
        assertEquals("core", circular.getCentralObject().getName());
    }

    /**
     * 测试轨道系统中心点的返回值
     */
    @Test
    public void testGetCentralObject() {
        circular.setCentralObject(center);
        assertEquals(center, circular.getCentralObject());
    }

    /**
     * addTrack(Track t)
     * 策略：按轨道的类型来划分：存在、不存在
     */
    @Test
    public void testAddTrack() {
//初始时轨道数为零
        assertEquals((Integer) 0, circular.getTrackNum());
        circular.addTrack(track1);
        assertTrue(circular.getSortedTracks().contains(track1)); //确认添加成功
//添加轨道后变为1
        assertEquals((Integer) 1, circular.getTrackNum());

        circular.addTrack(track1);
        assertFalse(circular.addTrack(track1)); //确认添加失败
        assertEquals((Integer) 1, circular.getTrackNum());
    }

    /**
     * removeTrack(Track t)
     * 策略：按轨道的类型来划分：存在、不存在
     */
    @Test
    public void testRemoveTrack() {
        assertTrue(circular.addTrack(track1));
//初始时轨道数为零
        assertEquals((Integer) 1, circular.getTrackNum());
        assertTrue(circular.removeTrack(track1));//删除成功
//删除轨道后变为0
        assertEquals((Integer) 0, circular.getTrackNum());

        assertFalse(circular.removeTrack(track1));//删除失败
        assertEquals((Integer) 0, circular.getTrackNum());
    }
    /**
     * getTrackNum()
     * 测试轨道数量的返回值
     */
    @Test
    public void testGetTrackNum() {
        assertEquals((Integer) 0, circular.getTrackNum());
        List<Track> tracks = Arrays.asList(track1, track2, track3);
        tracks.forEach(circular::addTrack);
        assertEquals((Integer) 3, circular.getTrackNum());

        circular.removeTrack(track3);
        assertEquals((Integer) 2, circular.getTrackNum());
    }

    /**
     * getTrackObjectNum(Track t)
     * 策略：
     * 按轨道的类型来划分：存在、不存在
     * 按轨道上的物体划分：轨道上存在物体、轨道上不存在物体
     */
    @Test
    public void testGetTrackObjectNum() {
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track2, object3);

        //t1、t2轨道上存在物体
        assertEquals((Integer) 1, circular.getTrackObjectNum(track1));
        assertEquals((Integer) 2, circular.getTrackObjectNum(track2));
        //t3轨道上不存在物体
        assertEquals((Integer) 0, circular.getTrackObjectNum(track3));
        //轨道系统不存在轨道t4
    }

    /**
     * getObjectOnTrack(Track t)
     * 策略：
     * 按轨道的类型来划分：存在、不存在
     * 按轨道上的物体划分：轨道上存在物体、轨道上不存在物体
     */
    @Test
    public void testGetObjectOnTrack() {

        List<PhysicalObject> test1 = new ArrayList<>(Arrays.asList(object1, object2));
        List<PhysicalObject> test2 = new ArrayList<>(Collections.singletonList(object3));
        List<PhysicalObject> test3 = new ArrayList<>();

        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);

        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track2, object3);

        assertEquals(test1, circular.getObjectOnTrack(track1));
        assertEquals(test2, circular.getObjectOnTrack(track2));
        assertFalse(circular.getObjectOnTrack(track1).contains(object3));
        assertEquals(test3, circular.getObjectOnTrack(track3));
    }
    /**
     * addObjectToTrack(Track t, E object)
     * 策略：
     * 按轨道的存在与否划分：不存在、存在
     * 按该物体是否存在在该轨道上划分：存在、不存在
     */
    @Test
    public void testAddObjectToTrack() {
        // 轨道不存在
        assertFalse(circular.addObjectToTrack(track1, object5));

        // 轨道存在
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object4);

        // 物体不存在
        assertTrue(circular.addObjectToTrack(track1, object5));

        // 物体存在
        assertFalse(circular.addObjectToTrack(track1, object4));

        // 验证添加成功
        List<PhysicalObject> expectedObjects = Arrays.asList(object4, object5);
        assertEquals(expectedObjects, circular.getObjectOnTrack(track1));
    }
    /**
     * removeObjectOnTrack(Track t, E object)
     * 策略：
     * 按轨道的存在与否划分：不存在、存在
     * 按该物体是否存在在该轨道上划分：存在、不存在
     */
    @Test
    public void testRemoveObjectOnTrack() {
        //轨道不存在
        assertFalse(circular.removeObjectOnTrack(track1, object5));
        //轨道存在
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object4);
        circular.addObjectToTrack(track1, object3);
        //物体不存在
        assertFalse(circular.removeObjectOnTrack(track1, object5));
        //物体存在
        assertTrue(circular.removeObjectOnTrack(track1, object4));
        //验证删除成功
        assertTrue(circular.getObjectOnTrack(track1).contains(object3));
        assertFalse(circular.getObjectOnTrack(track1).contains(object4));
    }
    /**
     * addTrackRelation(E object1, E object2, double weight)
     * 策略：
     * 按物体的类型来划分：存在、不存在或者为同一个物体
     * 按权重的取值范围划分：等于0、非零
     */
    @Test
    public void testAddTrackRelation() {
        // 添加轨道和物体
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular.addTrack(track4);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);
        circular.addObjectToTrack(track4, object4);

        // 同一个物体
        assertFalse(circular.addtrackRelation(object1, object1, 1));

        // 物体2不存在于轨道系统
        assertFalse(circular.addtrackRelation(object1, object6, 1));

        // 物体1和物体2不存在于轨道系统
        assertFalse(circular.addtrackRelation(object5, object6, 1));

        // 物体1不存在于轨道系统
        assertFalse(circular.addtrackRelation(object6, object1, 1));

        // 物体存在
        circular.addObjectToTrack(track1, object5);
        circular.addtrackRelation(object1, object2, 1);
        circular.addtrackRelation(object1, object3, 1);
        circular.addtrackRelation(object1, object4, 1);
        circular.addtrackRelation(object1, object5, 1);

        // 权重输入不合法
        assertFalse(circular.addtrackRelation(object2, object5, 0));

        // 判断关系添加成功
        assertTrue(circular.getTrackConnectedObject(object1).contains(object2));
        assertTrue(circular.getTrackConnectedObject(object1).contains(object3));
        assertTrue(circular.getTrackConnectedObject(object1).contains(object4));
        assertTrue(circular.getTrackConnectedObject(object1).contains(object5));
        assertEquals((Integer) 1, circular.getLogicalDistance(object1, object2));
        assertEquals((Integer) 1, circular.getLogicalDistance(object1, object3));
    }
    /**
     *  addCentralRelation(E object, double weight)
     *  策略：
     * 按物体的类型来划分：存在、不存在
     * 按权重的取值范围划分：等于0、非零
     */
    @Test
    public void testAddCentralRelation() {
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track1, object3);
        circular.addObjectToTrack(track1, object4);

        assertFalse(circular.addCentralRelation(object5, 1));//物体不存在于轨道系统
        //物体存在
        circular.addObjectToTrack(track1, object5);

        circular.addCentralRelation(object1, 1);
        circular.addCentralRelation(object2, 1);
        circular.addCentralRelation(object3, 1);
        circular.addCentralRelation(object4, 1);

        assertFalse(circular.addCentralRelation(object5, 0));//权重输入不合法

        Set<PhysicalObject> centralConnectedObject = circular.getCentralConnectedObject();
        assertTrue(centralConnectedObject.contains(object1));
        assertTrue(centralConnectedObject.contains(object2));
        assertTrue(centralConnectedObject.contains(object3));
        assertTrue(centralConnectedObject.contains(object4));

    }
    /**
     *
     * boolean removeTrackRelation(E object1, E object2)
     * 策略：
     * 按物体的类型来划分：存在、不存在
     * 按物体关系的类型来划分：存在、不存在
     */

    @Test
    public void testRemoveTrackRelation() {
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular.addTrack(track4);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);
        circular.addObjectToTrack(track4, object4);
        circular.addObjectToTrack(track1, object5);

        assertFalse(circular.removetrackRelation(object1, object6));//轨道系统的关系集合中不存在这两个物体
        //关系集合存在这两个物体
        assertFalse(circular.removetrackRelation(object2, object5));//关系不存在

        //判断关系删除成功
        circular.removetrackRelation(object1, object3);
        circular.removetrackRelation(object1, object4);
        circular.removetrackRelation(object1, object5);
        circular.removetrackRelation(object1, object2);
        assertFalse(circular.getTrackConnectedObject(object1).contains(object2));
        assertFalse(circular.getTrackConnectedObject(object1).contains(object3));
        assertFalse(circular.getTrackConnectedObject(object1).contains(object4));
        assertFalse(circular.getTrackConnectedObject(object1).contains(object5));
    }

    /**
     * removeCentralRelation(E object)
     * 策略：
     * 按物体的类型来划分：存在、不存在
     */

    @Test
    public void testRemoveCentralRelation() {
// 设置环形结构中心对象和轨道
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track1, object3);
        circular.addObjectToTrack(track1, object4);
        circular.addObjectToTrack(track1, object5);
        // 添加中心关系
        circular.addCentralRelation(object1, 1);
        circular.addCentralRelation(object2, 1);
        circular.addCentralRelation(object3, 1);
        circular.addCentralRelation(object4, 1);

// 验证关系不存在时，removeCentralRelation() 方法返回 false
        assertFalse(circular.removeCentralRelation(object5));

// 验证删除中心关系后，中心对象和相关对象之间的关系删除成功
        circular.removeCentralRelation(object2);
        circular.removeCentralRelation(object3);
        circular.removeCentralRelation(object4);
        assertTrue(circular.getCentralConnectedObject().contains(object1));
        assertFalse(circular.getCentralConnectedObject().contains(object2));
        assertFalse(circular.getCentralConnectedObject().contains(object3));
        assertFalse(circular.getCentralConnectedObject().contains(object4));

    }
    /**
     * transit(E oldObject, E newObject, Track t)
     * 策略：
     * 按轨道或者物体是否存在划分：存在、不存在
     */
    @Test
    public void testTransit() {
// 设置轨道中心对象和轨道，添加对象
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        // 验证物体不存在时，transit() 方法返回 false
        assertFalse(circular.transit(object3, object4, track1));

// 验证轨道不存在时，transit() 方法返回 false
        assertFalse(circular.transit(object1, object3, track3));

// 验证转移成功后，物体已经在目标轨道上
        assertTrue(circular.transit(object1, object3, track2));
        assertTrue(circular.getObjectOnTrack(track2).contains(object3));
    }
    /**
     * move(E oldObject, E newObject)
     * 策略：
     * 按物体是否存在划分：存在、不存在
     */
    @Test
    public void testMove() {
// 添加轨道和物体
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        // 验证物体不存在时，move() 方法返回 false
        assertFalse(circular.move(object3, object4));

// 验证移动成功后，物体已经在目标轨道上
        assertTrue(circular.move(object1, object3));
        assertTrue(circular.getObjectOnTrack(track1).contains(object3));
    }

    /**
     * calculateEntropyOfOrbit()
     * 策略：
     * 按不同轨道系统的信息熵程度划分
     * 比较三个轨道系统的信息熵
     */
    @Test
    public void testCalculateEntropyOfOrbit() {
// 创建三个环形轨道系统
        CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular3 = new ConcreteCircularOrbit<>();
        // 添加轨道和物体
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular1.addTrack(track1);
        circular1.addTrack(track2);
        circular1.addTrack(track3);
        circular2.addTrack(track1);
        circular2.addTrack(track2);
        circular2.addTrack(track3);
        circular3.addTrack(track1);
        circular3.addTrack(track2);

        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);

        circular1.addObjectToTrack(track1, object1);
        circular1.addObjectToTrack(track1, object2);
        circular1.addObjectToTrack(track1, object3);

        circular2.addObjectToTrack(track1, object1);
        circular2.addObjectToTrack(track1, object2);
        circular2.addObjectToTrack(track2, object3);

        circular3.addObjectToTrack(track1, object1);
        circular3.addObjectToTrack(track2, object2);

// 比较三个轨道系统的信息熵大小
        assertTrue(circular.calculateEntropyOfOrbit() > circular1.calculateEntropyOfOrbit());
        assertTrue(circular.calculateEntropyOfOrbit() > circular2.calculateEntropyOfOrbit());
        assertTrue(circular2.calculateEntropyOfOrbit() > circular1.calculateEntropyOfOrbit());
    }
    /**
     * getLogicalDistance(E object1, E object2)
     * 测试逻辑距离
     */
    @Test
    public void testGetLogicalDistance() {
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular.addTrack(track4);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);
        circular.addObjectToTrack(track4, object4);
        circular.addObjectToTrack(track1, object5);
        circular.addObjectToTrack(track4, object6);
        circular.addtrackRelation(object1, object3, 1);
        circular.addtrackRelation(object1, object5, 1);
        circular.addtrackRelation(object2, object3, 1);
        circular.addtrackRelation(object3, object4, 1);
        circular.addtrackRelation(object4, object5, 1);
        assertEquals((Integer) 1, circular.getLogicalDistance(object2, object3));//距离最接近
        assertEquals((Integer) 0, circular.getLogicalDistance(object1, object1));//自己到自己
        assertEquals((Integer) (-1), circular.getLogicalDistance(object1, object6));//不是朋友
    }

    /**
     * contains(E object)
     * 策略：
     * 按物体的类型划分：存在、不存在
     */
    @Test
    public void testContains() {
        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track2, object3);
        //物体存在
        assertTrue(circular.contains(object1));
        assertTrue(circular.contains(object2));
        assertTrue(circular.contains(object3));
        //物体不存在
        assertFalse(circular.contains(object4));
        assertFalse(circular.contains(object5));
    }
    /**
     *  getTrackOfObject(E object)
     * 按物体类型划分：存在、不存在
     */
    @Test
    public void testGetTrackOfObject() {
        circular.setCentralObject(center);
        Stream.of(track1, track2, track3).forEach(circular::addTrack);
        Stream.of(object1, object2).forEach(o -> circular.addObjectToTrack(track1, o));
        circular.addObjectToTrack(track2, object3);

        assertEquals(track1, circular.getTrackOfObject(object1));
        assertEquals(track1, circular.getTrackOfObject(object2));
        assertEquals(track2, circular.getTrackOfObject(object3));
        assertNull(circular.getTrackOfObject(object4));
        assertNull(circular.getTrackOfObject(object5));

    }

    /**
     * getCentralConnectedObject()
     * 按与中心物体有关系的物体集合类型划分：空集合、非空集合
     */
    @Test
    public void testGetCentralConnectedObject() {
        Set<PhysicalObject> test = new HashSet<>();

        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track1, object3);
        circular.addObjectToTrack(track1, object4);
        circular.addObjectToTrack(track1, object5);

//空集合
        assertEquals(test, circular.getCentralConnectedObject());

//非空集合
        test.addAll(Stream.of(object1, object2, object3, object4)
                .peek(ob -> circular.addCentralRelation(ob, 1))
                .collect(Collectors.toSet()));

        assertEquals(test, circular.getCentralConnectedObject());

    }
    /**
     * getTrackConnectedObject(E object)
     * 策略：
     * 按与object物体有关系的物体集合类型划分：空集合、非空集合
     * 按与object物体类型划分：存在、不存在
     */
    @Test
    public void testGetTrackConnectedObject() {
        Set<PhysicalObject> test1 = new HashSet<>();


        circular.setCentralObject(center);
        circular.addTrack(track1);
        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track1, object2);
        circular.addObjectToTrack(track1, object3);
        circular.addObjectToTrack(track1, object4);

        circular.addObjectToTrack(track1, object5);
        //空集合
        assertEquals(test1, circular.getTrackConnectedObject(object1));
        //非空集合
        test1.add(object5);
        test1.add(object2);
        test1.add(object3);
        test1.add(object4);

        //添加关系


        circular.addtrackRelation(object1, object2, 1);
        circular.addtrackRelation(object1, object3, 1);
        circular.addtrackRelation(object1, object4, 1);
        circular.addtrackRelation(object1, object5, 1);
        circular.addtrackRelation(object2, object3, 1);
        circular.addtrackRelation(object2, object4, 1);
        circular.addtrackRelation(object2, object5, 1);
        circular.addtrackRelation(object3, object4, 1);
        circular.addtrackRelation(object3, object5, 1);
        circular.addtrackRelation(object4, object5, 1);

        assertEquals(test1, circular.getTrackConnectedObject(object1));
    }

    /**
     * geDifference(CircularOrbit<L, E> that)
     */
    @Test
    public void testGeDifference() {
        CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<>();

        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);

        circular2.addTrack(track1);
        circular2.addTrack(track2);

        circular1.addTrack(track1);
        circular1.addTrack(track2);
        circular1.addTrack(track3);
        circular1.addTrack(track4);

        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);
        circular.addObjectToTrack(track1, object1);

        circular1.addObjectToTrack(track3, object3);
        circular1.addObjectToTrack(track4, object4);
        circular1.addObjectToTrack(track1, object1);
        circular1.addObjectToTrack(track2, object2);

        circular2.addObjectToTrack(track1, object5);
        circular2.addObjectToTrack(track2, object2);
        circular2.addObjectToTrack(track1, object3);
        circular2.addObjectToTrack(track2, object4);

        Difference diffTest1 = circular.getDifference(circular1);
        Difference diffTest2 = circular.getDifference(circular2);


        System.out.println(diffTest1.toString());
        System.out.println(diffTest2.toString());

    }

    /**
     * getSortedTracks()
     * 按轨道集合划分：空集合、非空集合
     */
    @Test
    public void testGetSortedTracks() {
        List<Track> test = new ArrayList<>();
//空集合
        assertEquals(test, circular.getSortedTracks());
//非空集合
//乱序输入
        circular.addTrack(track2);
        circular.addTrack(track1);
        circular.addTrack(track3);
        circular.addTrack(track4);
        test.add(track1);
        test.add(track2);
        test.add(track3);
        test.add(track4);
        List<Track> sortedTracks = circular.getSortedTracks().stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals(test, sortedTracks);

    }

    /**
     * Method: drawPicture()
     * 测试可视化
     */
    @Test
    public void testDrawPicture() {
        CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<>();
        CentralObject center = new CentralObject("sun");
        Track t1 = new Track("track1", 100);
        PhysicalObject ob1 = new PhysicalObject("object1");
        circular.setCentralObject(center);
        circular.addTrack(t1);
        circular.addObjectToTrack(t1, ob1);
        circular.drawPicture();
    }

    /**
     * checkOrbitAvailable()
     * 测试合法性
     * 在具体子类中测试
     */
    @Test
    public void testCheckOrbitAvailable() {
        assertTrue(circular.checkOrbitAvailable());
    }

    /**
     * 测试Iterator
     */
    @Test
    public void testIterator() {
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        Track track1 = Track.getInstance("track1", 1.0);
        Track track2 = Track.getInstance("track2", 2.0);
        Track track3 = Track.getInstance("track3", 3.0);
        PhysicalObject po1 = PhysicalObject.getInstance("po1");
        PhysicalObject po2 = PhysicalObject.getInstance("po2");
        PhysicalObject po3 = PhysicalObject.getInstance("po3");
        PhysicalObject po4 = PhysicalObject.getInstance("po4");
        PhysicalObject po5 = PhysicalObject.getInstance("po5");

        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addObjectToTrack(track1, po1);
        concreteCircularOrbit.addObjectToTrack(track1, po3);
        concreteCircularOrbit.addObjectToTrack(track1, po4);
        concreteCircularOrbit.addObjectToTrack(track2, po2);
        concreteCircularOrbit.addObjectToTrack(track3, po5);

        Iterator<PhysicalObject> iterator = concreteCircularOrbit.iterator();
        String result = "po1po3po4po2po5";
        StringBuilder test = new StringBuilder();
        String tmp;
        while (iterator.hasNext()) {
            tmp = iterator.next().getName();
            System.out.println(tmp);
            test.append(tmp);
        }
        assertEquals(result, test.toString());
    }
}