package applications;

import APIs.CircularOrbitAPIs;
import applications.TrackGame.Athlete;
import applications.TrackGame.Strategy.RandomStrategy;
import applications.TrackGame.Strategy.RecordStrategy;
import applications.TrackGame.Strategy.Strategy;
import applications.TrackGame.TrackCircularOrbit;
import applications.TrackGame.TrackCircularOrbitBuilder;
import centralObject.CentralObject;
import org.junit.Test;
import track.Track;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrackCircularOrbitTest {
    /**
     * Method: checkOrbitAvailable()
     * 验证轨道系统合法性
     */
    @Test
    public void checkOrbitAvailableTest() throws IOException {
        AtomicInteger gameType = new AtomicInteger();
        AtomicInteger trackNum = new AtomicInteger();
        final boolean[] state = new boolean[1];

        List<Athlete> athleteList = new LinkedList<>();
        TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
        BufferedReader in = new BufferedReader(new FileReader("src/txt/TrackGame_Medium.txt"));
        String athletePattern = "Athlete\\s*::=\\s*<(\\w+),(\\w+),(\\w+),(\\w+),([\\w,]+)>";
        String gamePattern = "Game\\s*::=\\s*([\\w,]+)";
        String trackPattern = "NumOfTracks\\s*::=\\s*(\\w+)";

        in.lines().forEach(fileLine -> {
            Matcher athleteMatcher = Pattern.compile(athletePattern).matcher(fileLine);
            Matcher gameMatcher = Pattern.compile(gamePattern).matcher(fileLine);
            Matcher trackMatcher = Pattern.compile(trackPattern).matcher(fileLine);
            if (athleteMatcher.find()) {
                String nameString = athleteMatcher.group(1);
                int idNum = Integer.parseInt(athleteMatcher.group(2));
                String nationality = athleteMatcher.group(3);
                int age = Integer.parseInt(athleteMatcher.group(4));
                double bestRecord = Double.parseDouble(athleteMatcher.group(5));
                Athlete a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
                athleteList.add(a);
            } else if (gameMatcher.find()) {
                gameType.set(Integer.parseInt(gameMatcher.group(1)));
            } else if (trackMatcher.find()) {
                trackNum.set(Integer.parseInt(trackMatcher.group(1)));
            }
        });

        // 随机分配赛道
        RandomStrategy strategy = new RandomStrategy();

        List<Track> trackList = new ArrayList<>();
        int[] DefaultRadius = new int[8];
        for (int i = 0; i < 8; i++) {
            DefaultRadius[i] = 50 + 100 * i;
        }
        for (int i = 0; i < trackNum.get(); i++) {
            trackList.add(new Track("track" + i, DefaultRadius[i]));
        }
        List<Map<Track, List<Athlete>>> arrangementMap = strategy.Arrange(new ArrayList<>(athleteList), trackList);

        int OrbitNum = arrangementMap.size();

        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (int i = 0; i < OrbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
            int finalI = i;
            future = future.thenComposeAsync((Void) -> {
                trackBuilder.createCircularOrbit(String.valueOf(gameType.get()));
                trackBuilder.buildTracks(trackList);
                trackBuilder.buildPhysicalObjects(new CentralObject("Orbit" + finalI), currentMap);
                TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
                state[0] = CircularOrbitAPIs.checkOrbitAvailable(newOrbit);
                if (!state[0]) {
                    throw new IllegalStateException("轨道系统不合法（赛道分配不正确），构建失败");
                }
                return CompletableFuture.completedFuture(null);
            });
        }
        future.join();

        //按记录成绩分组
        Strategy strategy1 = new RecordStrategy();
        List<Track> trackList1 = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            DefaultRadius[i] = 50 + 100 * i;
        }

        for (int i = 0; i < trackNum.get(); i++) {
            trackList1.add(new Track("track" + i, DefaultRadius[i]));
        }
        arrangementMap = strategy1.Arrange(new ArrayList<>(athleteList), trackList1);

        OrbitNum = arrangementMap.size();

        for (int i = 0; i < OrbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
            trackBuilder.createCircularOrbit();
            trackBuilder.buildTracks(trackList1);
            trackBuilder.buildPhysicalObjects(new CentralObject("Orbit" + i), currentMap);
            TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
            state[0] = CircularOrbitAPIs.checkOrbitAvailable(newOrbit);
            assert (state[0]) : "轨道系统不合法（赛道分配不正确），构建失败";
        }
    }
    @Test
    public void testArrange1() //随机策略
    {
        List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
        Track t1 = new Track("track1", 100);
        Track t2 = new Track("track2", 200);
        Track t3 = new Track("track3", 300);
        Track t4 = new Track("track4", 400);
        Athlete a1 = Athlete.getInstance("aa", 1, "AAA", 11, 1);
        Athlete a2 = Athlete.getInstance("bb", 2, "AAA", 11, 2);
        Athlete a3 = Athlete.getInstance("cc", 3, "AAA", 11, 3);
        Strategy strategy = new RandomStrategy();
        boolean state;
        TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
        List<Athlete> athletes = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();

        athletes.add(a1);
        athletes.add(a2);
        athletes.add(a3);
        tracks.add(t1);
        tracks.add(t2);
        tracks.add(t3);
        tracks.add(t4);

        List<Map<Track, List<Athlete>>> arrangementMap = strategy.Arrange(new ArrayList<>(athletes), tracks);
        int OrbitNum = arrangementMap.size();
        trackOrbitList.clear();

// 遍历所有赛道分组，构建对应的轨道系统
        for (int i = 0; i < OrbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);

            // 构建当前轨道系统
            trackBuilder.createCircularOrbit();
            trackBuilder.buildTracks(tracks);
            trackBuilder.buildPhysicalObjects(new CentralObject("Orbit" + i), currentMap);
            TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();

            // 检查轨道系统是否合法
            state = CircularOrbitAPIs.checkOrbitAvailable(newOrbit);
            assert (state) : "轨道系统不合法（赛道分配不正确），构建失败";

            // 将构建好的轨道系统加入列表中
            trackOrbitList.add(newOrbit);
        }

// 输出所有轨道系统的信息
        for (TrackCircularOrbit currentOrbit : trackOrbitList) {
            System.out.println(currentOrbit.toString());
        }

    }
    @Test
    public void testArrange2()//按成绩策略
    {
        List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
        Track t1 = new Track("track1", 100);
        Track t2 = new Track("track2", 200);
        Track t3 = new Track("track3", 300);
        Track t4 = new Track("track4", 400);
        Athlete a1 = Athlete.getInstance("aa", 1, "AAA", 11, 1);
        Athlete a2 = Athlete.getInstance("bb", 2, "AAA", 11, 2);
        Athlete a3 = Athlete.getInstance("cc", 3, "AAA", 11, 3);
        Strategy strategy = new RecordStrategy();
        boolean state;
        TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
        List<Athlete> athletes = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();

        athletes.add(a1);
        athletes.add(a2);
        athletes.add(a3);
        tracks.add(t1);
        tracks.add(t2);
        tracks.add(t3);
        tracks.add(t4);

        List<Map<Track, List<Athlete>>> arrangementMap = strategy.Arrange(new ArrayList<>(athletes), tracks);
        int OrbitNum=arrangementMap.size();

        for (int i = 0; i < OrbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);

            trackBuilder.createCircularOrbit();
            trackBuilder.buildTracks(tracks);
            trackBuilder.buildPhysicalObjects(new CentralObject("Orbit" + i), currentMap);
            TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
            state = CircularOrbitAPIs.checkOrbitAvailable(newOrbit);
            assert (state):"轨道系统不合法（赛道分配不正确），构建失败";
        }
        for (TrackCircularOrbit CurrentOrbit : trackOrbitList) {
            System.out.println(CurrentOrbit.toString());
        }
    }
}
