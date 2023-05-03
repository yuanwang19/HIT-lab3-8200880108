package circularOrbit;

import centralObject.CentralObject;
import org.junit.Test;

import static org.junit.Assert.*;

import otherDirectory.Difference;
import physicalObject.PhysicalObject;
import track.Track;

import java.util.*;

public class CircularOrbitBuilderTest {
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

    List<Track> tracks = Arrays.asList(track1, track2, track3, track4);
    List<PhysicalObject> physicalObjectList1 = Arrays.asList(object1, object2);
    List<PhysicalObject> physicalObjectList2 = Arrays.asList(object3, object4);
    List<PhysicalObject> physicalObjectList3 = Collections.singletonList(object5);
    List<PhysicalObject> physicalObjectList4 = Collections.singletonList(object6);
    Map<Track, List<PhysicalObject>> ObjectMap = new HashMap<>();

    CircularOrbitBuilder<CentralObject, PhysicalObject> circularOrbitBuilder = new CircularOrbitBuilder<CentralObject, PhysicalObject>() {
        @Override
        public void createCircularOrbit() {
            this.concreteCircularOrbit = new ConcreteCircularOrbit<>();
        }
    };

    @Test
    public void testCreateCircularOrbit() {
        circularOrbitBuilder.createCircularOrbit();
        assertNotNull(circularOrbitBuilder.getConcreteCircularOrbit());
    }

    @Test
    public void testGetConcreteCircularOrbit() {
        circularOrbitBuilder.createCircularOrbit();
        assertEquals(ConcreteCircularOrbit.class, circularOrbitBuilder.getConcreteCircularOrbit().getClass());
    }

    @Test
    public void testBuildTracks() {
        circularOrbitBuilder.createCircularOrbit();
        circularOrbitBuilder.buildTracks(tracks);

        ConcreteCircularOrbit<CentralObject, PhysicalObject> test = new ConcreteCircularOrbit<>();
        tracks.forEach(test::addTrack);
        assertEquals(test.getSortedTracks(), circularOrbitBuilder.getConcreteCircularOrbit().getSortedTracks());
    }

    @Test
    public void testBuildPhysicalObjects() {
        ObjectMap.put(track1, physicalObjectList1);
        ObjectMap.put(track2, physicalObjectList2);
        ObjectMap.put(track3, physicalObjectList3);
        ObjectMap.put(track4, physicalObjectList4);

        circularOrbitBuilder.createCircularOrbit();
        circularOrbitBuilder.buildTracks(tracks);
        circularOrbitBuilder.buildPhysicalObjects(center, ObjectMap);

        ConcreteCircularOrbit<CentralObject, PhysicalObject> test = new ConcreteCircularOrbit<>();
        test.setCentralObject(center);
        tracks.forEach(test::addTrack);
        ObjectMap.forEach((track, objectList) -> objectList.forEach(object -> test.addObjectToTrack(track, object)));

        Difference difference = test.getDifference(circularOrbitBuilder.getConcreteCircularOrbit());

        assertEquals(test.getCentralObject(), circularOrbitBuilder.getConcreteCircularOrbit().getCentralObject());
        System.out.println(difference.toString());
    }
}
