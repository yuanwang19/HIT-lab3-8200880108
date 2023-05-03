package APIs;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import org.junit.Test;
import otherDirectory.Difference;
import physicalObject.PhysicalObject;
import track.Track;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CircularOrbitAPIsTest {
    // Testing strategy:
    // 覆盖所有重要方法，尽可能覆盖所有分支
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
     * getObjectDistributionEntropy(CircularOrbit<L,E> c)
     */
    @Test
    public void testGetObjectDistributionEntropy() {
        CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<>();

        circular.addTrack(track1);
        circular.addTrack(track2);
        circular.addTrack(track3);

        circular1.addTrack(track1);
        circular1.addTrack(track2);
        circular1.addTrack(track3);

        circular2.addTrack(track1);
        circular2.addTrack(track2);
        circular2.addTrack(track3);

        circular.addObjectToTrack(track1, object1);
        circular.addObjectToTrack(track2, object2);
        circular.addObjectToTrack(track3, object3);

        circular1.addObjectToTrack(track1, object1);
        circular1.addObjectToTrack(track1, object2);
        circular1.addObjectToTrack(track1, object3);

        circular2.addObjectToTrack(track1, object1);
        circular2.addObjectToTrack(track1, object2);
        circular2.addObjectToTrack(track2, object3);

//比较三个轨道系统的信息熵
        double entropy_circular = CircularOrbitAPIs.getObjectDistributionEntropy(circular);
        double entropy_circular1 = CircularOrbitAPIs.getObjectDistributionEntropy(circular1);
        double entropy_circular2 = CircularOrbitAPIs.getObjectDistributionEntropy(circular2);
        assertTrue(entropy_circular > entropy_circular1);
        assertTrue(entropy_circular > entropy_circular2);
        assertTrue(entropy_circular2 > entropy_circular1);
    }

    /**
     * Method: getLogicalDistance(CircularOrbit<L,E> c, E e1, E e2)
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
        circular.addObjectToTrack(track1, object6);
        circular.addtrackRelation(object1, object3, 1);
        circular.addtrackRelation(object2, object3, 1);

        assertEquals(1, CircularOrbitAPIs.getLogicalDistance(circular, object2, object3), 0.0);//距离最接近
        assertEquals(0, CircularOrbitAPIs.getLogicalDistance(circular, object1, object1), 0.0);//自己到自己
        assertEquals(-1, CircularOrbitAPIs.getLogicalDistance(circular, object1, object6), 0.0);//不是朋友
    }



    /**
     * getDifference(CircularOrbit<L,E> c1, CircularOrbit<L,E> c2)
     */
    @Test
    public void testGetDifference() {
        CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<>();

        Track track1 = new Track("track1", 10.0);
        Track track2 = new Track("track2", 20.0);
        Track track3 = new Track("track3", 30.0);
        Track track4 = new Track("track4", 40.0);

        PhysicalObject object1 = new PhysicalObject("obj1");
        PhysicalObject object2 = new PhysicalObject("obj2");
        PhysicalObject object3 = new PhysicalObject("obj3");
        PhysicalObject object4 = new PhysicalObject("obj4");
        PhysicalObject object5 = new PhysicalObject("obj5");

        circular1.addTrack(track1);
        circular1.addTrack(track2);
        circular1.addTrack(track3);
        circular1.addTrack(track4);

        circular2.addTrack(track1);
        circular2.addTrack(track2);

        circular1.addObjectToTrack(track1, object1);
        circular1.addObjectToTrack(track2, object2);
        circular1.addObjectToTrack(track3, object3);
        circular1.addObjectToTrack(track4, object4);

        circular2.addObjectToTrack(track1, object5);
        circular2.addObjectToTrack(track2, object2);
        circular2.addObjectToTrack(track1, object3);
        circular2.addObjectToTrack(track2, object4);

        Difference diffTest1 = CircularOrbitAPIs.getDifference(circular, circular1);
        Difference diffTest2 = CircularOrbitAPIs.getDifference(circular, circular2);

        System.out.println(diffTest1.toString());
        System.out.println(diffTest2.toString());

    }

    /**
     * checkOrbitAvailable(CircularOrbit<L,E> c)
     * 在具体应用实现中测试
     */
    @Test
    public void testCheckOrbitAvailable() {
        assertTrue(CircularOrbitAPIs.checkOrbitAvailable(circular));
    }
}