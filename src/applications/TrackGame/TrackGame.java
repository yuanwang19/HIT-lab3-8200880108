package applications.TrackGame;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import applications.TrackGame.Strategy.RandomStrategy;
import applications.TrackGame.Strategy.RecordStrategy;
import applications.TrackGame.Strategy.Strategy;
import centralObject.CentralObject;
import track.Track;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TrackGame {

    // Abstraction function:
    // AF是一个保存着比赛各样数据和操作的抽象数据型到真实的比赛管理系统的映射

    // Representation invariant:
    // 轨道不能重名，不能有相同半径
    // 不能有重号的运动员

    // Safety from rep exposure:
    // 设置关键数据private final
    // 在有必要的时候使用防御性拷贝

    private Integer gameType;
    private Integer trackNum;
    private final List<Athlete> athleteList = new LinkedList<>();
    private final List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
    private final TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();

    /**
     * 菜单
     */
    public void GameMenu() {
        System.out.println("1.\t读取文件1");
        System.out.println("2.\t读取文件2");
        System.out.println("3.\t读取文件3");
        System.out.println("4.\t随机分配赛道");
        System.out.println("5.\t按成绩分配赛道");
        System.out.println("6.\t删除运动员");
        System.out.println("7.\t删除赛道");
        System.out.println("8.\t增加新赛道");
        System.out.println("9.\t增加新运动员");
        System.out.println("10.\t计算信息熵");
        System.out.println("11.\t交换两名选手的赛道和组别");
        System.out.println("12.\t可视化");
        System.out.println("13.\t显示分组信息");
//		System.out.println("14.\t4人接力赛");
        System.out.println("end.\t 结束");
    }

    /**
     * 比赛
     */
    public void gameMain() throws IOException {
        String inputString;
        String[] arguments;
        boolean exitFlag = false;
        final Pattern ATHLETE_PATTERN = Pattern.compile("Athlete\\s*::=\\s*<(\\w+),(\\w+),(\\w+),(\\w+),([\\w,.]+)>");
        final Pattern GAME_PATTERN = Pattern.compile("Game\\s*::=\\s*([\\w,.]+)");
        final Pattern TRACK_PATTERN = Pattern.compile("NumOfTracks\\s*::=\\s*(\\w+)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            GameMenu();
            String input = reader.readLine().trim();
            switch (input) {
        				/*
Athlete：匹配字符串"Athlete"。
\\s*：匹配零个或多个空格。
::=：匹配字符串"::="。
\\s*：匹配零个或多个空格。
<：匹配字符"<"。
(\\w+)：匹配一个或多个字母、数字或下划线，并将其分组为第一个捕获组，表示运动员的姓名。
,：匹配逗号。
(\\w+)：匹配一个或多个字母、数字或下划线，并将其分组为第二个捕获组，表示运动员的编号。
,：匹配逗号。
(\\w+)：匹配一个或多个字母、数字或下划线，并将其分组为第三个捕获组，表示运动员的国籍。
,：匹配逗号。
(\\w+)：匹配一个或多个字母、数字或下划线，并将其分组为第四个捕获组，表示运动员的年龄。
,：匹配逗号。
([\\w,\\.]+)：匹配一个或多个字母、数字、逗号或句点，并将其分组为第五个捕获组，表示运动员的最好成绩。
				 */
				/*
Game：匹配字符串"Game"。
\\s*：匹配零个或多个空格。
::=：匹配字符串"::="。
\\s*：匹配零个或多个空格。
([\\w,\\.]+)：匹配一个或多个字母、数字、逗号或句点，并将其分组为第一个捕获组，表示比赛类型。
				 */
				/*
NumOfTracks：匹配字符串"NumOfTracks"。
\\s*：匹配零个或多个空格。
::=：匹配字符串"::="。
\\s*：匹配零个或多个空格。
(\\w+)：匹配一个或多个字母、数字或下划线，并将其分组为第一个捕获组，表示跑道数量。
				 */
                case "1":// 读取文件
                    try (BufferedReader in = new BufferedReader(new FileReader("src/txt/TrackGame.txt"))) {
                        String fileLine;
                        while ((fileLine = in.readLine()) != null) {
                            Matcher athleteMatcher = ATHLETE_PATTERN.matcher(fileLine);
                            Matcher gameMatcher = GAME_PATTERN.matcher(fileLine);
                            Matcher trackMatcher = TRACK_PATTERN.matcher(fileLine);
                            if (athleteMatcher.find()) {
                                String nameString = athleteMatcher.group(1);
                                int idNum = Integer.parseInt(athleteMatcher.group(2));
                                String nationality = athleteMatcher.group(3);
                                int age = Integer.parseInt(athleteMatcher.group(4));
                                double bestRecord = Double.parseDouble(athleteMatcher.group(5));
                                Athlete a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
                                athleteList.add(a);
                            } else if (gameMatcher.find()) {
                                gameType = Integer.valueOf(gameMatcher.group(1));
                            } else if (trackMatcher.find()) {
                                trackNum = Integer.valueOf(trackMatcher.group(1));
                            }
                        }
                        System.out.println("文件读取成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try (BufferedReader in = new BufferedReader(new FileReader("src/txt/TrackGame_Medium.txt"))) {
                        String fileLine;
                        while ((fileLine = in.readLine()) != null) {
                            Matcher athleteMatcher = ATHLETE_PATTERN.matcher(fileLine);
                            Matcher gameMatcher = GAME_PATTERN.matcher(fileLine);
                            Matcher trackMatcher = TRACK_PATTERN.matcher(fileLine);
                            if (athleteMatcher.find()) {
                                String nameString = athleteMatcher.group(1);
                                int idNum = Integer.parseInt(athleteMatcher.group(2));
                                String nationality = athleteMatcher.group(3);
                                int age = Integer.parseInt(athleteMatcher.group(4));
                                double bestRecord = Double.parseDouble(athleteMatcher.group(5));
                                Athlete a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
                                athleteList.add(a);
                            } else if (gameMatcher.find()) {
                                gameType = Integer.valueOf(gameMatcher.group(1));
                            } else if (trackMatcher.find()) {
                                trackNum = Integer.valueOf(trackMatcher.group(1));
                            }
                        }
                        System.out.println("文件读取成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    try (BufferedReader in = new BufferedReader(new FileReader("src/txt/TrackGame_Larger.txt"))) {
                        String fileLine;
                        while ((fileLine = in.readLine()) != null) {
                            Matcher athleteMatcher = ATHLETE_PATTERN.matcher(fileLine);
                            Matcher gameMatcher = GAME_PATTERN.matcher(fileLine);
                            Matcher trackMatcher = TRACK_PATTERN.matcher(fileLine);
                            if (athleteMatcher.find()) {
                                String nameString = athleteMatcher.group(1);
                                int idNum = Integer.parseInt(athleteMatcher.group(2));
                                String nationality = athleteMatcher.group(3);
                                int age = Integer.parseInt(athleteMatcher.group(4));
                                double bestRecord = Double.parseDouble(athleteMatcher.group(5));
                                Athlete a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
                                athleteList.add(a);
                            } else if (gameMatcher.find()) {
                                gameType = Integer.valueOf(gameMatcher.group(1));
                            } else if (trackMatcher.find()) {
                                trackNum = Integer.valueOf(trackMatcher.group(1));
                            }
                        }
                        System.out.println("文件读取成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":// 随机分配赛道
                    Strategy strategy1 = new RandomStrategy();
                    this.arrangeOrbit(strategy1);
                    arrangeOutPut();
                    System.out.println("分组完成\n");
                    break;
                case "5":// 按成绩分配赛道
                    Strategy strategy2 = new RecordStrategy();
                    this.arrangeOrbit(strategy2);
                    arrangeOutPut();
                    System.out.println("分组完成\n");
                    break;
                case "6":// 删除运动员
                    System.out.println("请输入需要删除的运动员的id：");
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    int id0 = Integer.parseInt(arguments[0]);

                    boolean isRemoved = false;
                    for (Athlete athlete : athleteList) {
                        if (athlete.getIdNum() == id0) {
                            athleteList.remove(athlete);
                            System.out.println("删除成功！");
                            isRemoved = true;
                            break;
                        }
                    }
                    if (!isRemoved) {
                        System.out.println("删除失败！");
                    }
                    break;

                case "7":// 删除赛道
                    trackNum--;
                    System.out.println("轨道数-1");
                    System.out.println("当前轨道数：" + trackNum);
                    System.out.println("请重新分配方案");
                    break;
                case "8":// 增加轨道数
                    trackNum++;
                    System.out.println("轨道数：" + trackNum + "\n");
                    System.out.println("请重新分配方案\n");
                    break;
                case "9":// 增加运动员
                    System.out.println("请输入需要增加的运动员的名字、id、国籍、年龄、最好成绩\n");
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    String nameString = arguments[0];
                    int idNum = Integer.parseInt(arguments[1]);
                    String nationality = arguments[2];
                    int age = Integer.parseInt(arguments[3]);
                    double bestRecord = Double.parseDouble(arguments[4]);
                    Athlete a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
                    athleteList.add(a);
                    System.out.println("添加成功\n");
                    break;
                case "10":// 计算信息熵
                    System.out.println("输入需要计算信息熵的轨道系统序号");
                    System.out.println("输入范围为0——" + (trackOrbitList.size() - 1));
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    int currentOrbit = Integer.parseInt(arguments[0]);
                    System.out.println("信息熵为：" + trackOrbitList.get(currentOrbit).calculateEntropyOfOrbit() + "\n");
                    break;
                case "11":
                    // 交换两名选手的赛道和组别
                    System.out.println("请输入需要交换的两名运动员的id\n");
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    int id1 = Integer.parseInt(arguments[0]);
                    int id2 = Integer.parseInt(arguments[1]);
                    Athlete a1 = null;
                    Athlete a2 = null;

                    for (Athlete athlete : athleteList) {
                        if (athlete.getIdNum() == id1) {
                            a1 = athlete;
                        }
                        if (athlete.getIdNum() == id2) {
                            a2 = athlete;
                        }
                    }

                    Track t1 = null;
                    Track t2 = null;
                    TrackCircularOrbit o1 = null;
                    TrackCircularOrbit o2 = null;

                    if (a1 == null || a2 == null) {
                        System.out.println("选手不存在");
                        break;
                    }

                    for (TrackCircularOrbit orbit : trackOrbitList) {
                        if (orbit.contains(a1)) {
                            t1 = orbit.getTrackOfObject(a1);
                            o1 = orbit;
                        }
                        if (orbit.contains(a2)) {
                            t2 = orbit.getTrackOfObject(a2);
                            o2 = orbit;
                        }
                    }

                    Objects.requireNonNull(o1).removeObjectOnTrack(t1, a1);
                    Objects.requireNonNull(o2).removeObjectOnTrack(t2, a2);
                    o1.addObjectToTrack(t1, a2);
                    o2.addObjectToTrack(t2, a1);
                    System.out.println("交换成功");

                    break;

                case "12":// 可视化
                    System.out.println("输入需要显示的轨道系统序号");
                    System.out.println("输入范围为0——" + (trackOrbitList.size() - 1));
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    int currentOrbit1 = Integer.parseInt(arguments[0]);
                    CircularOrbitHelper.visualize(trackOrbitList.get(currentOrbit1));
                    break;
                case "13":// 显示分组信息
                    arrangeOutPut();
                    break;
            }

        }while (!exitFlag);
    }
    /**
     * 按不同策略进行比赛分组
     * @param strategy 策略
     */
    public void arrangeOrbit(Strategy strategy) {
        boolean state;
        List<Track> trackList = new ArrayList<>();
        int[] defaultRadii = new int[8];
        for (int i = 0; i < 8; i++) {
            defaultRadii[i] = 50 + 100 * i;
            trackList.add(new Track("track" + i, defaultRadii[i]));
        }

        List<Map<Track, List<Athlete>>> arrangementMap = strategy.Arrange(new ArrayList<>(athleteList), trackList);
        int orbitNum = arrangementMap.size();

        trackOrbitList.clear();
        for (int i = 0; i < orbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);

            trackBuilder.createCircularOrbit(String.valueOf(gameType));
            trackBuilder.buildTracks(trackList);
            trackBuilder.buildPhysicalObjects(new CentralObject("Orbit" + i), currentMap);

            TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
            state = CircularOrbitAPIs.checkOrbitAvailable(newOrbit);
            assert state : "轨道系统不合法（赛道分配不正确），构建失败";
            trackOrbitList.add(newOrbit);
        }

    }
    /**
     * 打印安排
     */
    public void arrangeOutPut() {
        for (TrackCircularOrbit CurrentOrbit : trackOrbitList) {
            System.out.println(CurrentOrbit.toString());
        }
    }

}
