package applications.AtomStructure;

import circularOrbit.ConcreteCircularOrbit;
import track.Track;

import java.util.List;


public class AtomCircularOrbit extends ConcreteCircularOrbit<AtomicNucleus, Electron> {
    // Abstraction function:
    // AF（elementName）= 该原子轨道结构的元素名

    // Representation invariant:
    // 轨道不能重名，不能有轨道具有相同半径

    // Safety from rep exposure:
    // 同父类
    // 在有必要的时候使用防御性拷贝


    /**
     * 无参构造函数
     */
    public AtomCircularOrbit() {
        super();
    }

    /**
     * 实现跃迁：将一个轨道上的一个电子移到另一个轨道，没有电子返回false
     *
     * @param fromTrack 轨道1
     * @param toTrack 轨道2
     * @return 是否移动成功
     */
    public boolean transit(Track fromTrack, Track toTrack) {
        List<Electron> fromElectrons = OrbitMap.get(fromTrack);
        List<Electron> toElectrons = OrbitMap.get(toTrack);

        if (fromElectrons.size() >= 1) {
            toElectrons.add(Electron.getInstance());
            fromElectrons.remove(0);
            return true;
        }

        return false;
    }



    /**
     * 从某条轨道删去一个电子，因为电子互相之间没有区别，所以只需一个轨道参数。
     *
     * @param track 轨道
     * @return 是否删除成功
     */
    public boolean removeElectron(Track track) {
        List<Electron> electrons = OrbitMap.get(track);

        if (electrons.size() >= 1) {
            electrons.remove(0);
            return true;
        }

        return false;
    }


    /**
     * 验证轨道系统合法性
     * 电子在从内到外的轨道上的分布数目应满足2、8、18、…的合理分布。
     *
     * @return 是否合法
     */
    @Override
    public boolean checkOrbitAvailable() {
        List<Track> trackList = this.getSortedTracks();
        StringBuilder electronicStructure = new StringBuilder(); // 电子排布
        int atomicNumber = 0;
        int[] electronCounts = {2, 2, 6, 2, 6, 10, 6, 2, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 10, 6, 2, 14, 10, 6, 2, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6, 2, 14, 10, 6};

        for (Track track : trackList) {
            atomicNumber += getTrackObjectNum(track);
        }

        if (atomicNumber <= 2) {
            electronicStructure.append("1s").append(atomicNumber);
        } else {
            int totalElectrons = 0;
            for (int i = 0; i < electronCounts.length; i++) {
                totalElectrons += electronCounts[i];
                if (atomicNumber <= totalElectrons) {
                    int electronsInLastShell = electronCounts[i] - (totalElectrons - atomicNumber);
                    for (int j = 1; j <= i + 1; j++) {
                        electronicStructure.append(j == 1 ? "" : " ");
                        electronicStructure.append(j == i + 1 ? electronsInLastShell + "" : electronCounts[j - 1] + "");
                        electronicStructure.append(j == i ? "s" + electronsInLastShell : j <= 2 ? "s" + electronCounts[j - 1] : "p" + (j - 2));
                    }
                    break;
                }
            }
        }

        System.out.println("该原子电子排布为" + electronicStructure);
        return true;
    }

    /**
     * 重写toString方法
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(this.getCentralObject().getName()).append(":\n");

        List<Track> trackList = this.getSortedTracks();
        for (Track currentTrack : trackList) {
            int electronsOnTrack = OrbitMap.get(currentTrack).size();
            sb.append(currentTrack.getName()).append("上有：").append(electronsOnTrack).append("个电子\n");
        }

        return sb.toString();

    }
}
