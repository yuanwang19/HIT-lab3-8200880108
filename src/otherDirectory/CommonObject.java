package otherDirectory;

import java.util.Objects;

public class CommonObject implements Comparable<CommonObject>{
    // Abstraction function:
    // AF（name）= 一般物体的标记
    // AF（pos）= 一般物体的位置

    // Representation invariant:
    // 名字不能为空

    // Safety from rep exposure:
    // 设置关键数据name为private final
    private final String Name;

    /**
     * 检查名字是否为空
     */
    public void checkRep() {
        assert this.Name!=null;
    }

    /**
     * 构造方法
     * @param Name
     */
    public CommonObject(String Name) {
        this.Name = Name;
        checkRep();
    }

    /**
     * 返回名字
     * @return 名字
     */
    public String getName() {
        return Name;
    }

    /**
     * 判断两个物体是否相同
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // 如果两个对象引用相同，那么它们肯定相等。
        if (obj == null) return false; // 如果 obj 为空，则它肯定不等于这个对象。

        if (getClass() != obj.getClass()) return false; // 如果 obj 不是 MyClass 类型的，那么它肯定不等于这个对象。
        CommonObject that = (CommonObject) obj;
        return this.Name.equals(that.Name) ;

    }

    /**
     * 比较方法
     * @param that the object to be compared.
     * @return
     */
    @Override
    public int compareTo(CommonObject that){
        return this.getName().compareTo(that.getName());
    }

    @Override
    public String toString() {
        return Name;
    }

    protected Position pos = new Position(0,0);

    public Position getPos() {
        return pos;
    }


}