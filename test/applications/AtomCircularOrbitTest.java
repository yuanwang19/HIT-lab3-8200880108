package applications;

import APIs.CircularOrbitAPIs;
import applications.AtomStructure.AtomCircularOrbit;
import applications.AtomStructure.AtomCircularOrbitBuilder;
import applications.AtomStructure.AtomicNucleus;
import applications.AtomStructure.Electron;
import org.junit.Test;
import track.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class AtomCircularOrbitTest {
    AtomCircularOrbit atomCircularOrbit = new AtomCircularOrbit();
    Track track1 = new Track("track1", 100);
    Track track2 = new Track("track2", 200);
    Track track3 = new Track("track3", 300);
    AtomicNucleus Nucleus = AtomicNucleus.getInstance();
    Electron electron = Electron.getInstance();
    Electron electron1 = Electron.getInstance();
    Electron electron2 = Electron.getInstance();

    @Test
    public void transitTest() {
        atomCircularOrbit.addTrack(track1);
        atomCircularOrbit.addTrack(track2);
        atomCircularOrbit.addTrack(track3);
        atomCircularOrbit.addObjectToTrack(track1, electron);
        atomCircularOrbit.addObjectToTrack(track2, electron1);
        atomCircularOrbit.addObjectToTrack(track3, electron2);
        atomCircularOrbit.transit(track1, track2);

        int numElectron = atomCircularOrbit.getTrackObjectNum(track2);

        if (numElectron == 2) {
            assertEquals(2, numElectron);
        } else {
            fail("Failed to transit electrons from track1 to track2.");
        }

        assertFalse(atomCircularOrbit.transit(track1, track2));

    }

    @Test
    public void removeElectronTest() {
        atomCircularOrbit.addTrack(track1);
        atomCircularOrbit.addTrack(track2);
        atomCircularOrbit.addTrack(track3);
        atomCircularOrbit.addObjectToTrack(track1, electron);
        atomCircularOrbit.addObjectToTrack(track2, electron1);
        atomCircularOrbit.addObjectToTrack(track3, electron2);
        atomCircularOrbit.removeElectron(track1);
        atomCircularOrbit.removeElectron(track2);

        int numElectron1 = atomCircularOrbit.getTrackObjectNum(track1);
        int numElectron2 = atomCircularOrbit.getTrackObjectNum(track2);

        if (numElectron1 == 0 && numElectron2 == 0) {
            assertEquals(0, numElectron1);
            assertEquals(0, numElectron2);
        } else {
            fail("Failed to remove electrons from tracks.");
        }

        assertFalse(atomCircularOrbit.removeElectron(track1));

    }

    /**
     * Method: checkOrbitAvailable()
     * 验证轨道系统合法性
     */
    @Test
    public void checkOrbitAvailableTest() throws IOException {
        int trackNum = 0;
        int[] DefaultRadius = new int[10];
        final AtomCircularOrbitBuilder atomOrbitBuilder = new AtomCircularOrbitBuilder();
        boolean state;

        //一般情况
        BufferedReader in = new BufferedReader(new FileReader("src/txt/AtomicStructure_Medium.txt"));
        String fileLine;
        String elementPattern = "ElementName\\s*::=\\s*([a-zA-Z]+)";
        String trackPattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
        String electronPattern = "NumberOfElectron\\s*::=\\s*([\\d+\\/\\d+,;]+)";
        while ((fileLine = in.readLine()) != null) {
            Matcher elementMatcher = Pattern.compile(elementPattern).matcher(fileLine);
            Matcher trackMatcher = Pattern.compile(trackPattern).matcher(fileLine);
            Matcher electronMatcher = Pattern.compile(electronPattern).matcher(fileLine);

            if (elementMatcher.find()) {
                Nucleus = AtomicNucleus.getInstance(elementMatcher.group(1));
            } else if (trackMatcher.find()) {
                trackNum = Integer.parseInt(trackMatcher.group(1));
            } else if (electronMatcher.find()) {
                for (int i = 0; i < 10; i++) {
                    DefaultRadius[i] = 50 + 100 * i;
                }
                List<Track> trackList = new ArrayList<>();
                Map<Track, List<Electron>> elementMap = new HashMap<>();
                String[] NUm = electronMatcher.group(1).split(";");

                for (int i = 0; i < NUm.length; i++) {
                    Track track = new Track("track" + i, DefaultRadius[i]);
                    trackList.add(track);
                    String[] value = NUm[i].split("/");
                    int objNum = Integer.parseInt(value[1]);
                    List<Electron> currentList = new LinkedList<>();

                    for (int j = 0; j < objNum; j++) {
                        Electron p = Electron.getInstance();
                        currentList.add(p);
                    }

                    elementMap.put(track, currentList);
                    assert (currentList.size() != 0) : "电子丢失，构建失败";
                }

                atomOrbitBuilder.createCircularOrbit();
                atomOrbitBuilder.buildTracks(trackList);
                atomOrbitBuilder.buildPhysicalObjects(Nucleus, elementMap);
                atomCircularOrbit = (AtomCircularOrbit) atomOrbitBuilder.getConcreteCircularOrbit();
            }

        }
        System.out.println("轨道数为：" + trackNum);
        state = CircularOrbitAPIs.checkOrbitAvailable(atomCircularOrbit);
        //与具体的原子电子排布进行对比
        assert (state) : "轨道系统不合法（电子不满足原子排布），构建失败";
        System.out.println(atomCircularOrbit.toString());

        //氢原子
        BufferedReader in1 = new BufferedReader(new FileReader("src/txt/AtomicStructure_Medium.txt"));
        while ((fileLine = in1.readLine()) != null) {
            Matcher elementMatcher = Pattern.compile(elementPattern).matcher(fileLine);
            Matcher trackMatcher = Pattern.compile(trackPattern).matcher(fileLine);
            Matcher electronMatcher = Pattern.compile(electronPattern).matcher(fileLine);

            if (elementMatcher.find()) {
                Nucleus = AtomicNucleus.getInstance(elementMatcher.group(1));
            } else if (trackMatcher.find()) {
                trackNum = Integer.parseInt(trackMatcher.group(1));
            } else if (electronMatcher.find()) {
                // 初始化轨道半径
                for (int i = 0; i < 10; i++) {
                    DefaultRadius[i] = 50 + 100 * i;
                }

                // 构建轨道和电子
                List<Track> trackList = new ArrayList<>();
                Map<Track, List<Electron>> elementMap = new HashMap<>();
                String[] NUm = electronMatcher.group(1).split(";");
                for (int i = 0; i < NUm.length; i++) {
                    Track track = new Track("track" + i, DefaultRadius[i]);
                    trackList.add(track);
                    String[] value = NUm[i].split("/");
                    int objNum = Integer.parseInt(value[1]);
                    List<Electron> currentList = new LinkedList<>();
                    for (int j = 0; j < objNum; j++) {
                        Electron p = Electron.getInstance();
                        currentList.add(p);
                    }
                    elementMap.put(track, currentList);
                    assert (currentList.size() != 0) : "电子丢失，构建失败";
                }

                // 构建原子轨道
                atomOrbitBuilder.createCircularOrbit();
                atomOrbitBuilder.buildTracks(trackList);
                atomOrbitBuilder.buildPhysicalObjects(Nucleus, elementMap);
                atomCircularOrbit = (AtomCircularOrbit) atomOrbitBuilder.getConcreteCircularOrbit();
            }

        }
        System.out.println("轨道数为：" + trackNum);
        state = CircularOrbitAPIs.checkOrbitAvailable(atomCircularOrbit);
        //与具体的原子电子排布进行对比
        assert (state) : "轨道系统不合法（电子不满足原子排布），构建失败";
        System.out.println(atomCircularOrbit.toString());
    }
}