package otherDirectory;


import java.util.List;
import java.util.Set;

public class Difference {
    // Abstraction function:
    // AF（trackNumDiff）= 记录轨道数的差异
    // AF（NumDiff）= 列表记录轨道上物体数目差异
    // AF=（ObjectDiff）= 记录物体差异

    // Representation invariant:
    // trackNumDiff NumDiff ObjectDiff 三者不为空

    // Safety from rep exposure:
    // 设置属性trackNumDiff，trackNumDiffNumDiff和ObjectDiff为private final

    private final Integer trackNumDiff;
    private final List<Integer> NumDiff;
    private final List<List<Set<String>>> ObjectDiff;

    /**
     * 三个属性不为空
     */
    public void checkRep(){
        assert trackNumDiff != null;
        assert NumDiff != null;
        assert ObjectDiff != null;
    }
    /**
     * 构造方法
     *
     * @param trackNumDiff 轨道数的差异
     * @param numDiff 轨道上物体数目差异
     * @param ObjectDiff 物体差异
     */
    public Difference(Integer trackNumDiff, List<Integer> numDiff, List<List<Set<String>>> ObjectDiff) {
        this.trackNumDiff = trackNumDiff;
        this.NumDiff = numDiff;
        this.ObjectDiff = ObjectDiff;
        checkRep();
    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("轨道数差异：").append(trackNumDiff).append("\n");
        for (int i = 0; i < NumDiff.size(); i++) {
            int num = i + 1;
            stringBuilder.append(String.format("轨道%d的物体数目差异：%d\n", num, NumDiff.get(i)));
        }
        for (int i = 0; i < ObjectDiff.size(); i++) {
            int num = i + 1;
            stringBuilder.append(String.format("轨道%d的物体差异：%s-%s\n", num, ObjectDiff.get(i).get(0), ObjectDiff.get(i).get(1)));
        }
        checkRep();
        return stringBuilder.toString();
    }
}
