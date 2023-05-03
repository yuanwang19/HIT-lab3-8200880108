package APIs;

import circularOrbit.CircularOrbit;

public class CircularOrbitHelper {
    // Abstraction function:
    // 是一个circularOrbit的可视化功能的调用者

    // Representation invariant:
    // circularOrbit不能是null

    // Safety from rep exposure:
    // 在有必要的时候使用防御性拷贝
    /**
     * 可视化
     *
     * @param i 轨道系统
     */
    public static <L, E> void visualize(CircularOrbit<L, E> i) {
        i.drawPicture();
    }
}
