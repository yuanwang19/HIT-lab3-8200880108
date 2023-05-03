package applications.SocialNetworkCircle;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import track.Track;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SocialNetworkGame {
// Abstraction function:
// AF是一个映射，将抽象的人际关系数据和操作管理器映射到真实的人际关系层级管理器。

// Representation invariant:
// socialCircularOrbit 不为 null
// centralUser 不为 null

// Safety from rep exposure:
// 将关键数据 personList，centralUser 和 socialCircularOrbit 设置为 private
// 在必要时使用防御性拷贝来保护数据。

    private Person centralUser;
    private final List<Person> personList = new ArrayList<>();
    private final List<relationKeeper> keeperList = new ArrayList<>();
    private SocialNetCircularOrbit socialCircularOrbit = null;
    private final SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();

    /**
     * 菜单
     */

    public void GameMenu() {
        System.out.println("1.\t读取文件构造系统");
        System.out.println("2.\t可视化");
        System.out.println("3.\t打印轨道结构");
        System.out.println("4.\t查询好友位置");
        System.out.println("5.\t增加新的朋友");
        System.out.println("6.\t删除一个关系并重新整理");
        System.out.println("7.\t增加新的关系并重新整理");
        System.out.println("8.\t计算信息扩散度");
        System.out.println("9.\t计算熵值");
        System.out.println("10.\t计算两个用户之间的逻辑距离");
        System.out.println("end.\t 结束");
    }
    /**
     * 主程序
     */
    public void gameMain() throws IOException {
        String inputString;
        String[] argu;
        String name1;
        String name2;
        Person person1;
        boolean state;
        boolean exitFlag = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            GameMenu();
            String input = reader.readLine().trim();
            switch (input) {
                case "1":
// 读取文件
                    File file = new File("src/txt/SocialNetworkCircle.txt");
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            if (line.isEmpty()) continue;

                            String centralUserPatternString = "CentralUser\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
                            Matcher centralUserMatcher = Pattern.compile(centralUserPatternString).matcher(line);
                            if (centralUserMatcher.find()) {
                                String name = centralUserMatcher.group(1);
                                int age = Integer.parseInt(centralUserMatcher.group(2));
                                String gender = centralUserMatcher.group(3);
                                centralUser = Person.getInstance(name, age, gender);
                            }

                            String friendPatternString = "Friend\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
                            Matcher friendMatcher = Pattern.compile(friendPatternString).matcher(line);
                            if (friendMatcher.find()) {
                                String name = friendMatcher.group(1);
                                int age = Integer.parseInt(friendMatcher.group(2));
                                String gender = friendMatcher.group(3);
                                Person person = Person.getInstance(name, age, gender);
                                personList.add(person);
                                socialCircularOrbitBuilder.createCircularOrbit();
                            }

                            String socialTiePatternString = "SocialTie\\s*::=\\s*<(\\w+),\\s*(\\w+),\\s*([01]\\.{1}\\d+)>";
                            Matcher socialTieMatcher = Pattern.compile(socialTiePatternString).matcher(line);
                            if (socialTieMatcher.find()) {
                                double weight = Double.parseDouble(socialTieMatcher.group(3));
                                relationKeeper currKeeper = new relationKeeper(socialTieMatcher.group(1), socialTieMatcher.group(2), weight);
                                keeperList.add(currKeeper);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("文件未找到");
                    }

// 检查读取是否成功
                    assert centralUser != null : "中心用户丢失，构建失败";
                    assert !personList.isEmpty() : "用户丢失，构建失败";
                    assert !keeperList.isEmpty() : "关系丢失，构建失败";

                    socialCircularOrbitBuilder.createCircularOrbit();
                    socialCircularOrbitBuilder.buildRelations(centralUser, personList, keeperList);
                    socialCircularOrbit = (SocialNetCircularOrbit) socialCircularOrbitBuilder.getConcreteCircularOrbit();
                    state = CircularOrbitAPIs.checkOrbitAvailable(socialCircularOrbit);

// 检查轨道系统是否合法
                    assert state : "轨道系统不合法（某人所在轨道不正确或某人不该存在），构建失败";
                    System.out.println("读取成功");
                    break;
                case "2":// 可视化
                    CircularOrbitHelper.visualize(socialCircularOrbit);
                    break;
                case "3":// 打印轨道结构
                    System.out.println(socialCircularOrbit.toString());
                    break;
                case "4": {
                    System.out.println("请输入需要查询的好友名字:");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    String nameString = argu[0];
                    Person p1 = personList.stream().filter(p -> p.getName().equals(nameString)).findFirst().orElse(null);
                    if (p1 == null) {
                        System.out.println("不存在这个人");
                        break;
                    }
                    Track track = socialCircularOrbit.getTrackOfObject(p1);
                    System.out.println("目标位于" + track.getName());
                    break;
                }
                case "5": // 增加新的朋友
                    System.out.println("增加新的朋友");
                    System.out.println("输入名字 岁数 性别");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    String name = argu[0];
                    int age = Integer.parseInt(argu[1]);
                    String gender = argu[2];
                    Person person7 = new Person(name, age, gender);
                    personList.add(person7);
                    break;
                case "6":// 删除一个关系并重新整理
                    System.out.println("删除一个关系");
                    System.out.println("输入名字1 名字2");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    name1 = argu[0];
                    name2 = argu[1];

                    String finalName = name1;
                    Optional<Person> optionalPerson1 = personList.stream()
                            .filter(Person -> Person.getName().equals(finalName))
                            .findFirst();
                    String finalName1 = name2;
                    Optional<Person> optionalPerson2 = personList.stream()
                            .filter(Person -> Person.getName().equals(finalName1))
                            .findFirst();
                    Person p1 = optionalPerson1.get();
                    Person p2 = optionalPerson2.get();

                    if (p1.getName().equals(centralUser.getName())) {
                        socialCircularOrbit.addCentralRelation(p2, 0);
                    } else if (p2.getName().equals(centralUser.getName())) {
                        socialCircularOrbit.addCentralRelation(p1, 0);
                    } else {
                        socialCircularOrbit.addtrackRelation(p1, p2, 0);
                        socialCircularOrbit.addtrackRelation(p2, p1, 0);
                    }

                    socialCircularOrbit.rearrangeOrbits();
                    break;
                case "7":// 增加新的关系并重新整理
                    System.out.println("增加新的关系");
                    System.out.println("输入名字1 名字2 权重");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    name1 = argu[0];
                    name2 = argu[1];
                    double weight = Double.parseDouble(argu[2]);

                    String finalName2 = name1;
                    Optional<Person> opt1 = personList.stream().filter(p -> p.getName().equals(finalName2)).findFirst();
                    String finalName3 = name2;
                    Optional<Person> opt2 = personList.stream().filter(p -> p.getName().equals(finalName3)).findFirst();
                    if (!opt1.isPresent() || !opt2.isPresent()) {
                        System.out.println("不存在这个人");
                        break;
                    }

                    Person person4 = opt1.get();
                    Person person5 = opt2.get();

                    if (person4.getName().equals(centralUser.getName())) {
                        socialCircularOrbit.addCentralRelation(person5, weight);
                    } else if (person5.getName().equals(centralUser.getName())) {
                        socialCircularOrbit.addCentralRelation(person4, weight);
                    } else {
                        socialCircularOrbit.addtrackRelation(person4, person5, weight);
                        socialCircularOrbit.addtrackRelation(person5, person4, weight);
                    }

                    socialCircularOrbit.rearrangeOrbits();

                    break;
                case "8"://计算熵值
                    System.out.println("信息熵为：" + socialCircularOrbit.calculateEntropyOfOrbit() + "\n");
                    break;
                case "9"://计算信息扩散度
                    System.out.println("计算信息扩散度:输入名字");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    name1 = argu[0];
                    String finalName4 = name1;
                    Optional<Person> optionalPerson3 = personList.stream()
                            .filter(person -> person.getName().equals(finalName4))
                            .findFirst();
                    person1 = null;
                    for (Person personTmp : personList) {
                        if (personTmp.getName().equals(name1)) {
                            person1 = personTmp;
                        }
                    }
                    if (person1 == null) {
                        System.out.println("不存在这个人");
                        break;
                    }
                    Person person6 = optionalPerson3.get();
                    double intimacy = socialCircularOrbit.computeIntimacy(person6);
                    System.out.println("信息扩散度为：" + intimacy + "\n");

                    break;
                case "10"://计算逻辑距离
                    System.out.println("计算逻辑距离");
                    System.out.println("输入名字1 名字2");
                    inputString = reader.readLine().trim();
                    argu = inputString.split("\\s");
                    name1 = argu[0];
                    name2 = argu[1];
                    String finalName5 = name1;
                    Person person8 = personList.stream().filter(person -> person.getName().equals(finalName5)).findFirst().orElse(null);
                    String finalName6 = name2;
                    Person person9 = personList.stream().filter(person -> person.getName().equals(finalName6)).findFirst().orElse(null);
                    if (person8 == null || person9 == null) {
                        System.out.println("不存在这个人");
                        break;
                    }
                    int logicalDistance = socialCircularOrbit.getLogicalDistance(person8, person9);
                    if (logicalDistance == -1) {
                        System.out.println("逻辑距离无穷大");
                    } else {
                        System.out.println("逻辑距离为：" + logicalDistance);
                    }

                    break;
                case "end":// 结束游戏
                    exitFlag = true;
                    break;
                default:
                    System.out.println("输入错误");
                    break;
            }


            }while (!exitFlag);
    }

}
