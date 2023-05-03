package applications.AtomStructure;

import circularOrbit.CircularOrbitBuilder;

/**
 * 原子轨道的构造类，输入中心原子核名称，电子排布，生成一个原子轨道系统系统。
 */
public class AtomCircularOrbitBuilder extends CircularOrbitBuilder<AtomicNucleus, Electron>{

    /**
     * 重写createCircularOrbit，返回AtomCircularOrbit对象
     */
    @Override
    public void createCircularOrbit() {
        this.concreteCircularOrbit = new AtomCircularOrbit();
    }

}
