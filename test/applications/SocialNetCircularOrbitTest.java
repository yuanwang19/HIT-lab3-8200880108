package applications;

import APIs.CircularOrbitAPIs;
import applications.SocialNetworkCircle.Person;
import applications.SocialNetworkCircle.SocialNetCircularOrbit;
import applications.SocialNetworkCircle.SocialNetCircularOrbitBuilder;
import applications.SocialNetworkCircle.relationKeeper;
import org.junit.Test;
import physicalObject.PhysicalObject;
import track.Track;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class SocialNetCircularOrbitTest {
    // Testing strategy:
    // 补测一些子类方法
    SocialNetCircularOrbit orbit = new SocialNetCircularOrbit();
    Person center = Person.getInstance("xxx", 20, "M");
    Person p1 = Person.getInstance("aa", 20, "M");
    Person p2 = Person.getInstance("bb", 20, "M");
    Track track1 = new Track("track1", 100);
    Track track2 = new Track("track2", 200);
    Track track3 = new Track("track3", 300);
    Track track4 = new Track("track4", 400);
    Person person = new Person("object1", 20, "M");
    Person person1 = new Person("object2", 21, "F");
    Person person2 = new Person("object3", 25, "F");
    Person person3 = new Person("object4", 22, "F");
    Person person4 = new Person("object5", 19, "M");
    Person person5 = new Person("object6", 29, "F");
    /**
     * getIntimacy()
     * 验证亲密度计算
     */
    @Test
    public void getIntimacyTest() {
        orbit.setCentralObject(center);
        orbit.addCentralRelation(p1, 1);
        orbit.addtrackRelation(p1, p2, 0.5);
        orbit.rearrangeOrbits();
        assertEquals(2.0, orbit.computeIntimacy(p2), 0.0);
    }

    /**
     * checkOrbitAvailable()
     * 验证轨道系统合法性
     */
    @Test
    public void checkOrbitAvailableTest() throws IOException {
        Person centralUser = null;
        List<Person> personList = new ArrayList<>();
        List<relationKeeper> keeperList = new ArrayList<>();
        SocialNetCircularOrbit socialCircularOrbit;
        SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();
        boolean state;
        BufferedReader in = new BufferedReader(new FileReader("src/txt/SocialNetworkCircle.txt"));
        String fileLine;
        String centralUserPatternString = "CentralUser\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
        String friendPatternString = "Friend\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
        String socialTiePatternString = "SocialTie\\s*::=\\s*<(\\w+),\\s*(\\w+),\\s*([01]\\.{1}\\d+)>";

        while ((fileLine = in.readLine()) != null) {
            Pattern centralUserPattern = Pattern.compile(centralUserPatternString);
            Pattern friendPattern = Pattern.compile(friendPatternString);
            Pattern socialTiePattern = Pattern.compile(socialTiePatternString);

            Matcher centralUserMatcher = centralUserPattern.matcher(fileLine);
            Matcher friendMatcher = friendPattern.matcher(fileLine);
            Matcher socialTieMatcher = socialTiePattern.matcher(fileLine);

            if (centralUserMatcher.find()) {
                String nameString = centralUserMatcher.group(1);
                int age = Integer.parseInt(centralUserMatcher.group(2));
                String gender = centralUserMatcher.group(3);
                centralUser = Person.getInstance(nameString, age, gender);
            } else if (friendMatcher.find()) {
                String nameString = friendMatcher.group(1);
                int age = Integer.parseInt(friendMatcher.group(2));
                String gender = friendMatcher.group(3);
                Person person = Person.getInstance(nameString, age, gender);
                personList.add(person);
                socialCircularOrbitBuilder.createCircularOrbit();
            } else if (socialTieMatcher.find()) {
                double weight = Double.parseDouble(socialTieMatcher.group(3));
                relationKeeper currKeeper = new relationKeeper(socialTieMatcher.group(1),
                        socialTieMatcher.group(2), weight);
                keeperList.add(currKeeper);
            }

        }
        socialCircularOrbitBuilder.createCircularOrbit();
        assert (centralUser != null) : "中心用户丢失，构建失败";
        assert (personList.size() != 0) : "用户丢失，构建失败";
        assert (keeperList.size() != 0) : "关系丢失，构建失败";
        socialCircularOrbitBuilder.buildRelations(centralUser, personList, keeperList);
        socialCircularOrbit = (SocialNetCircularOrbit) socialCircularOrbitBuilder.getConcreteCircularOrbit();
        state = CircularOrbitAPIs.checkOrbitAvailable(socialCircularOrbit);
        assert (state) : "轨道系统不合法（某人所在轨道不正确或某人不该存在），构建失败";
    }

    /**
     * addtrackRelation(E object1, E object2, double weight)
     * 按关系添加的有向性划分
     */
    @Test
    public void testAddtrackRelation() {
        orbit.setCentralObject(center);
        orbit.addTrack(track1);
        orbit.addTrack(track2);
        orbit.addTrack(track3);
        orbit.addTrack(track4);
        orbit.addObjectToTrack(track1, person);
        orbit.addObjectToTrack(track2, person1);
        orbit.addObjectToTrack(track3, person2);
        orbit.addObjectToTrack(track4, person3);
        orbit.addObjectToTrack(track1, person4);
        orbit.addObjectToTrack(track4, person5);

        orbit.addCentralRelation(person, 1);
        orbit.addCentralRelation(person4, 1);

        orbit.addtrackRelation(person, person1, 1);
        orbit.addtrackRelation(person, person2, 1);
        orbit.addtrackRelation(person, person4, 1);
        orbit.addtrackRelation(person1, person2, 1);
        orbit.addtrackRelation(person2, person5, 1);
        orbit.addtrackRelation(person2, person3, 1);

        assertFalse(orbit.addtrackRelation(person, person4, 1));//关系已存在
        assertTrue(orbit.addtrackRelation(person, person4, 0));//权重为0，在集合中删除关系
        assertFalse(orbit.getTrackConnectedObject(person).contains(person4));//验证关系已删除
        //判断关系添加成功
        assertTrue(orbit.getCentralConnectedObject().contains(person));
        assertTrue(orbit.getCentralConnectedObject().contains(person4));
    }

    /**
     * Method: addCentralRelation(E object, double weight)
     * 按关系添加的有向性划分
     */
    @Test
    public void testAddCentralRelation() {
        orbit.setCentralObject(center);
        orbit.addTrack(track1);
        orbit.addObjectToTrack(track1, person);
        orbit.addObjectToTrack(track1, person1);
        orbit.addObjectToTrack(track1, person2);
        orbit.addObjectToTrack(track1, person3);
        orbit.addObjectToTrack(track1, person4);

        orbit.addCentralRelation(person, 1);
        orbit.addCentralRelation(person1, 1);
        orbit.addCentralRelation(person2, 1);
        orbit.addCentralRelation(person3, 1);
        orbit.addCentralRelation(person4, 1);


        assertFalse(orbit.addCentralRelation(person3, 1));//关系已存在
        assertTrue(orbit.addCentralRelation(person4, 0));//权重为0，在集合中删除关系
        assertFalse(orbit.getCentralConnectedObject().contains(person4));//验证关系已删除
        //验证关系添加成功
        assertTrue(orbit.getCentralConnectedObject().contains(person));
        assertTrue(orbit.getCentralConnectedObject().contains(person1));
        assertTrue(orbit.getCentralConnectedObject().contains(person2));
        assertTrue(orbit.getCentralConnectedObject().contains(person3));
    }

    /**
     * Method: getTrackConnectedObject(E object)
     * 按关系添加的有向性划分
     */
    @Test
    public void testGetTrackConnectedObject() {
        Set<PhysicalObject> test1 = new HashSet<>();
        Set<PhysicalObject> test2 = new HashSet<>();
        Set<PhysicalObject> test3 = new HashSet<>();
        Set<PhysicalObject> test4 = new HashSet<>();

        orbit.setCentralObject(center);
        orbit.addTrack(track1);
        orbit.addObjectToTrack(track1, person);
        orbit.addObjectToTrack(track1, person1);
        orbit.addObjectToTrack(track1, person2);
        orbit.addObjectToTrack(track1, person3);
        orbit.addObjectToTrack(track1, person4);
        //空集合
        assertEquals(test1, orbit.getTrackConnectedObject(person));
        //非空集合
        test1.add(person4);
        test1.add(person1);
        test1.add(person2);
        test1.add(person3);
        test2.add(person4);
        test2.add(person);
        test2.add(person2);
        test3.add(person3);
        test3.add(person);
        test3.add(person1);
        test4.add(person4);
        test4.add(person);
        test4.add(person2);

        orbit.addtrackRelation(person, person1, 1);
        orbit.addtrackRelation(person, person2, 1);
        orbit.addtrackRelation(person, person3, 1);
        orbit.addtrackRelation(person, person4, 1);
        orbit.addtrackRelation(person1, person2, 1);
        orbit.addtrackRelation(person1, person4, 1);
        orbit.addtrackRelation(person2, person3, 1);
        orbit.addtrackRelation(person3, person4, 1);

        assertEquals(test1, orbit.getTrackConnectedObject(person));
        assertEquals(test2, orbit.getTrackConnectedObject(person1));
        assertEquals(test3, orbit.getTrackConnectedObject(person2));
        assertEquals(test4, orbit.getTrackConnectedObject(person3));
    }

    /**
     * Method: drawPicture()
     * 测试可视化
     */
    @Test
    public void testDrawPicture() {
        orbit.setCentralObject(center);
        orbit.drawPicture();
    }

    /**
     * Method: toString()
     * 测试转换为字符串
     */
    @Test
    public void testToString() {
        orbit.setCentralObject(center);
        System.out.println(orbit.toString());
    }
}
