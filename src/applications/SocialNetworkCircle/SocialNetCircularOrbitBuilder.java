package applications.SocialNetworkCircle;

import java.util.*;
import java.util.stream.Collectors;

import circularOrbit.CircularOrbitBuilder;
import track.Track;

/**
 * 人际关系的构造类，输入中心用户名称，社会关系和朋友关系，生成一个人际关系系统。
 */
public class SocialNetCircularOrbitBuilder extends CircularOrbitBuilder<Person, Person> {

    public SocialNetCircularOrbitBuilder() {
        concreteCircularOrbit = new SocialNetCircularOrbit();
    }

    /**
     * 创建具体类型的子类
     */
    @Override
    public void createCircularOrbit() {
        concreteCircularOrbit = new SocialNetCircularOrbit();
    }

    /**
     * 从关系网络生成轨道结构
     *
     * @param centralObj 中心
     * @param personList 人数表
     * @param keeperList 保存列表
     */
    public void buildRelations(Person centralObj, List<Person> personList, List<relationKeeper> keeperList) {
        Map<Track, List<Person>> orbitMap = new HashMap<>();
        List<Track> trackList = new ArrayList<>();
        concreteCircularOrbit.setCentralObject(centralObj);

        keeperList.forEach(keeper -> {
            Iterator<Person> iterator = personList.iterator();
            Person p1 = null;
            Person p2 = null;
            for (Person person : personList) {
                if (person.getName().equals(keeper.getFromString())) {
                    p1 = person;
                }
                if (centralObj.getName().equals(keeper.getFromString())) {
                    p1 = centralObj;
                }
                if (person.getName().equals(keeper.getToString())) {
                    p2 = person;
                }
            }

            assert p1 != null;
            if (p1.getName().equals(centralObj.getName())) {
                concreteCircularOrbit.addCentralRelation(p2, keeper.getWeight());
            } else {
                concreteCircularOrbit.addtrackRelation(p1, p2, keeper.getWeight());
            }
        });

        Set<Person> finishedPerson = new HashSet<>();
        Track track1 = new Track("track0", 50);
        orbitMap.put(track1, new ArrayList<>());
        trackList.add(track1);
        concreteCircularOrbit.getCentralConnectedObject().forEach(p -> {
            orbitMap.get(track1).add(p);
            finishedPerson.add(p);
        });
        int i = 0;
        boolean flag = true;
        while (flag) {
            flag = false;
            i++;
            Track t = new Track("track" + i, 50 + 100 * i);
            Set<Person> temSet = finishedPerson.stream()
                    .filter(p -> concreteCircularOrbit.getTrackConnectedObject(p).size() > 0)
                    .flatMap(p -> concreteCircularOrbit.getTrackConnectedObject(p).stream())
                    .filter(peo -> !finishedPerson.contains(peo))
                    .collect(Collectors.toSet());
            flag = !temSet.isEmpty();

            if (flag) {
                trackList.add(t);
                orbitMap.put(t, new ArrayList<>());
                orbitMap.get(t).addAll(temSet);
                finishedPerson.addAll(temSet);
            }
        }
        this.buildTracks(trackList);
        this.buildPhysicalObjects(centralObj, orbitMap);
    }


}
