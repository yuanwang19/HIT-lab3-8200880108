package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.CircularOrbitBuilder;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 * 径赛的构造类，输入径赛名称，比赛类型，跑道数目，运动员列表，生成一个径赛系统。
 */
public class TrackCircularOrbitBuilder extends CircularOrbitBuilder<CentralObject, Athlete> {

    /**
     * 创建具体类型的子类
     */
    public void createCircularOrbit(String gameType) {
        concreteCircularOrbit = new TrackCircularOrbit(gameType + "米");
    }

    /**
     * 创建具体类型的子类
     */
    @Override
    public void createCircularOrbit() {
        concreteCircularOrbit = new TrackCircularOrbit();
    }

    /**
     * 重写buildPhysicalObjects
     * 构造步骤：根据传入的centralObj和ObjectMap初始化trackCircularOrbit。
     *
     * @param centralObj 传入的中心物体
     * @param ObjectMap  传入的OrbitMap
     */
    @Override
    public void buildPhysicalObjects(CentralObject centralObj, Map<Track, List<Athlete>> ObjectMap) {
        TrackCircularOrbit circularOrbit = (TrackCircularOrbit) concreteCircularOrbit;
        circularOrbit.setCentralObject(centralObj);
        for (Map.Entry<Track, List<Athlete>> e : ObjectMap.entrySet()) {
            for (Athlete object : e.getValue()) {
                circularOrbit.addObjectToTrack(e.getKey(), object);
            }
        }
    }

}
