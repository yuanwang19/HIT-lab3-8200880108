package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.PhysicalObject;
import track.Track;

import java.util.List;

public class TrackCircularOrbit extends ConcreteCircularOrbit<CentralObject,Athlete> {
    // Abstraction function:
    // TrackCircularOrbit是一个由多个Track，多个轨道物体和中心物体组成的对轨道结构的抽象数据型
    // OrbitMap抽象轨道和物体的一对多的关系

    // Representation invariant:
    // 轨道不能重名，不能具有相同半径

    // Safety from rep exposure:
    // 在有必要的时候使用防御性拷贝
    String gameType = "未知";

    /**
     * 无参构造函数
     */
    public TrackCircularOrbit() {
    }

    /**
     * 有参构造函数，录入径赛种类
     *
     * @param gameType 径赛种类
     */
    public TrackCircularOrbit(String gameType) {
        super();
        this.centralObject = null;
        this.gameType = gameType;
    }

    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getCentralObject().getName()).append(":\n");
        List<Track> tracks = this.getSortedTracks();
        for (Track track : tracks) {
            for (Athlete athlete : OrbitMap.get(track)) {
                sb.append(track.getName())
                        .append(":")
                        .append(athlete.getName())
                        .append("\t号码：")
                        .append(athlete.getIdNum())
                        .append("\t国籍：")
                        .append(athlete.getNationality())
                        .append("\t年龄：")
                        .append(athlete.getAge())
                        .append("\t最好成绩：")
                        .append(athlete.getBestRecord())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 检验合法性
     * @return 是否合法
     */
    @Override
    public boolean checkOrbitAvailable() {
        boolean isSuitable =true;
        int numOfTrack = getTrackNum();
        isSuitable &= numOfTrack >= 4 && numOfTrack <= 10;
        int numOfRunner = OrbitMap.values().stream().mapToInt(List::size).sum();
        isSuitable &= numOfRunner <= numOfTrack;
        for (Track track : OrbitMap.keySet()) {
            isSuitable &= OrbitMap.get(track).size() <= 1;
        }
        return isSuitable;
    }

}
