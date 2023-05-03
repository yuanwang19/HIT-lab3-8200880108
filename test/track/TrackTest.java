package track;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrackTest {

    /**
     * getName()
     * 测试轨道名字的返回值
     */
    @Test
    public void testGetName() {
        Track track = Track.getInstance(1);
        String name = track.getName();
        assertEquals("track", name);
    }

    /**
     * getRadius()
     * 测试轨道半径的返回值
     */
    @Test
    public void testGetRadius() {
        Track track = Track.getInstance(10.0);
        double radius = track.getRadius();
        assertEquals(10.0, radius, 0.0);
    }

    /**
     * compareTo(Track t)
     * 策略
     * 按轨道的大小划分：较大、相等、较小
     */
    @Test
    public void testCompareTo() {
        List<Track> tracks = new ArrayList<>();
        for (Track track : Arrays.asList(
                Track.getInstance("a", 50),
                Track.getInstance("b", 150),
                Track.getInstance("c", 250),
                Track.getInstance("d", 350),
                Track.getInstance("e", 350)
        )) {
            tracks.add(track);
        }
        tracks.sort(Comparator.comparingDouble(Track::getRadius));

        assertEquals("a", tracks.get(0).getName());
        assertEquals("b", tracks.get(1).getName());
        assertEquals("c", tracks.get(2).getName());
        assertEquals("d", tracks.get(3).getName());
        assertEquals("e", tracks.get(4).getName());

    }

}