package applications.AtomStructure;

import centralObject.CentralObject;

/**
 * 原子系统的原子核，原子核视为单一物体，不考虑其中的中子和质子。
 */
public class AtomicNucleus extends CentralObject {
    /**
     * 静态工厂方法，返回原子核类
     *
     * @return 新的AtomicNucleus类
     */
    public static AtomicNucleus getInstance() {
        return new AtomicNucleus();
    }

    /**
     * 静态工厂方法，返回名字为name的原子核类
     *
     * @return 新的AtomicNucleus类
     */
    public static AtomicNucleus getInstance(String name) {
        return new AtomicNucleus(name);
    }

    /**
     * 继承来的构造方法
     *
     * @param name 名字
     */
    public AtomicNucleus(String name) {
        super(name);
    }

    /**
     * 无参构造一个名字为“AtomicNucleus”的原子核
     */
    public AtomicNucleus() {
        super("AtomicNucleus");
    }
}
