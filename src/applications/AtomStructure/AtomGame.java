package applications.AtomStructure;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import track.Track;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AtomGame {
    // Abstraction function:
    // AF是一个保存着原子轨道信息和电子核子信息的抽象数据型到真实的原子的映射

    // Representation invariant:
    // Nucleus,trackNum,atomCircularOrbit!=null

    // Safety from rep exposure:
    // 设置关键数据trackNum，Nucleus，atomCircularOrbit为private
    // 使用防御性拷贝
    private Integer trackNum;
    private AtomicNucleus Nucleus;
    private AtomCircularOrbit atomCircularOrbit = null;
    private final AtomCircularOrbitBuilder atomOrbitBuilder = new AtomCircularOrbitBuilder();
    public int[] DefaultRadius = new int[10];

    /**
     * 菜单
     */
    public void GameMenu() {
        System.out.println("1.\t读取文件构造系统");
        System.out.println("2.\t跃迁");
        System.out.println("3.\t可视化");
        System.out.println("4.\t打印轨道结构");
        System.out.println("5.\t增加新轨道");
        System.out.println("6.\t增加新电子");
        System.out.println("7.\t删除电子");
        System.out.println("8.\t删除整条轨道");
        System.out.println("9.\t计算熵值");
        System.out.println("end.\t 结束");
    }

    public void gameMain() throws IOException {
        String inputString;
        String[] arguments;
        boolean state;
        boolean exitFlag = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            GameMenu();
            String input = reader.readLine().trim();
            switch (input) {
                case "1":
                // 读取文件
                try (BufferedReader in = new BufferedReader(new FileReader("src/txt/AtomicStructure.txt"))) {
                    String fileLine;
                    String elementPattern = "ElementName\\s*::=\\s*([a-zA-Z]+)";
                    String trackPattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
                    String electronPattern = "NumberOfElectron\\s*::=\\s*([\\d+/,;]+)";
                    while ((fileLine = in.readLine()) != null) {
                        Matcher elementMatcher = Pattern.compile(elementPattern).matcher(fileLine);
                        Matcher trackMatcher = Pattern.compile(trackPattern).matcher(fileLine);
                        Matcher electronMatcher = Pattern.compile(electronPattern).matcher(fileLine);
                        if (elementMatcher.find()) {
                            Nucleus = AtomicNucleus.getInstance(elementMatcher.group(1));
                        } else if (trackMatcher.find()) {
                            trackNum = Integer.valueOf(trackMatcher.group(1));
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
                    assert (trackNum != null) : "轨道数丢失，构建失败";
                    state = CircularOrbitAPIs.checkOrbitAvailable(atomCircularOrbit);
                    //与具体的原子电子排布进行对比
                    assert (state) : "轨道系统不合法（电子不满足原子排布），构建失败";
                    System.out.println(atomCircularOrbit.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case"2":
                System.out.println("输入跃迁的起始轨道和终止轨道");
                System.out.printf("输入范围为0——%d%n", trackNum - 1);
                String inputString1 = reader.readLine().trim();
                String[] arguments2 = inputString1.split("\\s");
                int trackNum1 = Integer.parseInt(arguments2[0]);
                int trackNum2 = Integer.parseInt(arguments2[1]);
                Track track1 = atomCircularOrbit.getSortedTracks().get(trackNum1);
                Track track2 = atomCircularOrbit.getSortedTracks().get(trackNum2);
                if (atomCircularOrbit.transit(track1, track2)) {
                    System.out.println("跃迁成功");
                    System.out.println(atomCircularOrbit.toString());
                } else {
                    System.out.println("跃迁失败");
                }
                break;
                case "3":// 可视化
                    CircularOrbitHelper.visualize(atomCircularOrbit);
                    break;
                case "4":// 文字输出
                    System.out.println(atomCircularOrbit.toString());
                    break;
                case "6": // 增加新轨道
                    System.out.println("增加新轨道\n");
                    trackNum++;
                    Track newTrack = new Track("track" + (trackNum - 1), DefaultRadius[trackNum - 1]);
                    atomCircularOrbit.addTrack(newTrack);
                    System.out.printf("轨道数：%d\n%n", trackNum);
                    System.out.println(atomCircularOrbit.toString());
                    break;
                case "7":// 增加新电子
                    System.out.println("请输入需要增加电子的轨道");
                    System.out.print("输入范围为0——");
                    System.out.println(trackNum - 1);
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    int trackIndex1 = Integer.parseInt(arguments[0]);
                    Track t = atomCircularOrbit.getSortedTracks().get(trackIndex1);
                    atomCircularOrbit.addObjectToTrack(t, Electron.getInstance());
                    System.out.println(atomCircularOrbit.toString());
                    System.out.println("增加成功");

                    break;
                case "8":// 删除电子
                    System.out.println("请输入需要删除电子的轨道");
                    System.out.print("输入范围为0——");
                    System.out.println(trackNum - 1);
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    Track track3 = atomCircularOrbit.getSortedTracks().get(Integer.parseInt(arguments[0]));
                    boolean success = atomCircularOrbit.removeElectron(track3);
                    System.out.println(success ? "删除成功" : "删除失败");
                    System.out.println(atomCircularOrbit.toString());
                case "9":// 删除整条轨道
                    System.out.println("请输入需要删除的轨道");
                    System.out.printf("输入范围为0——%d\n", trackNum - 1);
                    inputString = reader.readLine().trim();
                    arguments = inputString.split("\\s");
                    Track track4 = atomCircularOrbit.getSortedTracks().get(Integer.parseInt(arguments[0]));
                    boolean success5 = atomCircularOrbit.removeTrack(track4);
                    System.out.println(success5 ? "删除成功" : "删除失败");
                    System.out.println(atomCircularOrbit.toString());
                    trackNum--;
                    break;
                case "10":// 计算熵值
                    System.out.println("信息熵为：" + atomCircularOrbit.calculateEntropyOfOrbit() + "\n");
                    break;
                case "end":// 结束游戏
                    exitFlag = true;
                    break;
                default:
                    System.out.println("输入错误");
                    break;

            }
        } while (!exitFlag);
    }

}
