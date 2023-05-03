package APIs;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import org.junit.Test;
import physicalObject.PhysicalObject;
import track.Track;

public class CircularOrbitHelperTest {
    /**
     * Method: visualize(CircularOrbit<L, E> c)
     * 看能否正常画图即可
     */
    @Test
    public void testVisualize() {
        CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<>();
        CircularOrbitHelper.visualize(circular);
    }
} 
