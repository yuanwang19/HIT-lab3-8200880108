package otherDirectory;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import org.junit.Test;
import physicalObject.PhysicalObject;
import track.Track;

import static org.junit.Assert.*;

public class DifferenceTest {
    // Testing strategy:
    // 覆盖所有重要方法，尽可能覆盖所有分支

    /**
     * toString()
     * 测试不同点toString的返回值
     */
    @Test
    public void testToString() {
        Track t1 = new Track("track1", 100);
        Track t2 = new Track("track2", 200);
        Track t3 = new Track("track3", 300);
        Track t4 = new Track("track4", 400);
        PhysicalObject ob1 = new PhysicalObject("object1");
        PhysicalObject ob2 = new PhysicalObject("object2");
        PhysicalObject ob3 = new PhysicalObject("object3");
        PhysicalObject ob4 = new PhysicalObject("object4");
        PhysicalObject ob5 = new PhysicalObject("object5");
        PhysicalObject ob6 = new PhysicalObject("object6");

        CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<>();
        CircularOrbit<CentralObject, PhysicalObject> circular3 = new ConcreteCircularOrbit<>();

        circular1.addTrack(t1);
        circular1.addTrack(t2);
        circular1.addTrack(t3);
        circular2.addTrack(t1);
        circular2.addTrack(t2);
        circular3.addTrack(t3);
        circular3.addTrack(t4);

        circular1.addObjectToTrack(t1, ob1);
        circular1.addObjectToTrack(t1, ob2);
        circular1.addObjectToTrack(t1, ob3);

        circular2.addObjectToTrack(t1, ob1);
        circular2.addObjectToTrack(t1, ob2);
        circular2.addObjectToTrack(t1, ob4);
        circular2.addObjectToTrack(t2, ob3);

        circular3.addObjectToTrack(t3, ob3);
        circular3.addObjectToTrack(t4, ob5);
        circular3.addObjectToTrack(t4, ob6);

        Difference difference1 = circular1.getDifference(circular2);
        Difference difference2 = circular1.getDifference(circular3);
        Difference difference3 = circular2.getDifference(circular3);

        //比较答案和打印结果
        String answer1 = "轨道数差异：1\n" +
                "轨道1的物体数目差异：0\n" +
                "轨道2的物体数目差异：-1\n" +
                "轨道3的物体数目差异：0\n" +
                "轨道1的物体差异：[object3]-[object4]\n" +
                "轨道2的物体差异：[]-[object3]\n" +
                "轨道3的物体差异：[]-[]\n";
        String answer2 = "轨道数差异：1\n" +
                "轨道1的物体数目差异：2\n" +
                "轨道2的物体数目差异：-2\n" +
                "轨道3的物体数目差异：0\n" +
                "轨道1的物体差异：[object2, object1]-[]\n" +
                "轨道2的物体差异：[]-[object6, object5]\n" +
                "轨道3的物体差异：[]-[]\n";
        String answer3 = "轨道数差异：0\n" +
                "轨道1的物体数目差异：2\n" +
                "轨道2的物体数目差异：-1\n" +
                "轨道1的物体差异：[object2, object1, object4]-[object3]\n" +
                "轨道2的物体差异：[object3]-[object6, object5]\n";

        assertEquals(answer1, difference1.toString());
        assertEquals(answer2, difference2.toString());
        assertEquals(answer3, difference3.toString());
    }
}
