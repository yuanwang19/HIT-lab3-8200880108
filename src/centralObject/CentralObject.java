package centralObject;

import otherDirectory.CommonObject;
public class CentralObject extends CommonObject {
    // Abstraction function:
    // AF（name）= 中心物体的标记

    // Representation invariant:
    // 名字不能为null

    // Safety from rep exposure:
    // 设置关键数据name为private final

    /**
     * 继承构造方法
     * 并使半径和角度均为零
     *
     * @param Name 名字
     */
    public CentralObject(String Name) {
        super(Name);
    }

    @Override
    public boolean equals(Object obj) {
        CentralObject that = (CentralObject) obj;
        return that.getName().equals(this.getName());
    }

    /**
     * 静态工厂方法
     *
     * @param centralName 中心点名字
     * @return 返回中心物体对象
     */
    public static CentralObject getInstance(String centralName) {
        return new CentralObject(centralName);
    }
}