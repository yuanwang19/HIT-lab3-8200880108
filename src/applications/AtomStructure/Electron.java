package applications.AtomStructure;

import physicalObject.PhysicalObject;

/**
 * 原子系统的电子，电子视为同一物体，不考虑不同电子的不同。
 */
public class Electron extends PhysicalObject {
    /**
     * 静态工厂方法，返回电子类
     *
     * @return 新的Electron类
     */
    public static Electron getInstance() {
        return new Electron();
    }

    /**
     * 无参构造一个名字为“electron”的电子
     */
    public Electron() {
        super("electron");
    }

    //在电子系统中存在多个电子 对象相同 但是需要通过引用来判别是否相同
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
    }
        return true; // 如果两个对象引用相同，那么它们肯定相等。
}

}
