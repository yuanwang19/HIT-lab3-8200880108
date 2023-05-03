package APIs;

import circularOrbit.CircularOrbit;
import otherDirectory.Difference;
public class CircularOrbitAPIs {
    // Abstraction function:
    // 是一个circularOrbit的某些功能的调用者

    // Representation invariant:
    // circularOrbit不能是null

    // Safety from rep exposure:
    // 在有必要的时候使用防御性拷贝

    /**
     * 计算信息熵
     * @param c
     * @return
     */
    public static<L, E> double getObjectDistributionEntropy(CircularOrbit<L,E> c) {
        return c.calculateEntropyOfOrbit();
    }

    /**
     * 返回逻辑距离
     * @param c
     * @param e1
     * @param e2
     * @return 逻辑距离
     */
    public static<L, E> int getLogicalDistance(CircularOrbit<L,E> c,E e1,E e2) {
        return c.getLogicalDistance(e1,e2);
    }

    /**
     *  返回两个轨道系统的不同
     * @param c1 轨道系统1
     * @param c2 轨道系统2
     * @return 两者的不同

     */
    public static<L, E> Difference getDifference(CircularOrbit<L,E> c1, CircularOrbit<L,E> c2) {
        return c1.getDifference(c2);
    }

    /**
     * 判断合法性
     * @param c
     * @return是否合法
     */
    public static<L, E> boolean checkOrbitAvailable(CircularOrbit<L,E> c) {
        return c.checkOrbitAvailable();
    }
}
