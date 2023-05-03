package circularOrbit;

import track.Track;

import java.util.List;
import java.util.Map;

/**
 * 轨道系统的构造类，输入轨道列表，中心物体和轨道上的物体集合，生成一个轨道系统。
 */
public abstract class CircularOrbitBuilder<L, E> {
    // Abstraction function:
    // AF（concreteCircularOrbit）= 绑定的轨道系统
    // 所以AF是从一个包含广义轨道系统的抽象数据型到现实具体应用轨道结构的映射

    // Representation invariant:
    // 轨道系统不能为空

    // Safety from rep exposure:
    // 设置关键数据concreteCircularOrbit为protected防止更改
    protected ConcreteCircularOrbit<L, E> concreteCircularOrbit;

    /**
     * 返回构造完成的对象
     *
     * @return ConcreteCircularOrbit
     */
    public ConcreteCircularOrbit<L, E> getConcreteCircularOrbit() {
        return concreteCircularOrbit;
    }

    /**
     * 子类实现时创建具体类型的子类
     */
    public abstract void createCircularOrbit();

    /**
     * 构造步骤：根据传入的trackList初始化concreteCircularOrbit
     *
     * @param trackList 轨道列表
     */
    public void buildTracks(List<Track> trackList) {
        for (Track t : trackList) {
            concreteCircularOrbit.addTrack(t);
        }
    }

    /**
     * 构造步骤：根据传入的centralObj和ObjectMap初始化concreteCircularOrbit
     *
     * @param centralObj 传入的中心物体
     * @param ObjectMap  传入的OrbitMap
     */
    public void buildPhysicalObjects(L centralObj, Map<Track, List<E>> ObjectMap) {
        // Set the central object of the circular orbit
        concreteCircularOrbit.setCentralObject(centralObj);

        // Add objects to tracks in the circular orbit
        for (Map.Entry<Track, List<E>> entry : ObjectMap.entrySet()) {
            Track track = entry.getKey();
            List<E> objects = entry.getValue();
            for (E object : objects) {
                concreteCircularOrbit.addObjectToTrack(track, object);
            }
        }

    }
}
