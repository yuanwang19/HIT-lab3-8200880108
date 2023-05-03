package physicalObject;
import otherDirectory.CommonObject;

import java.util.Objects;

public class PhysicalObject extends CommonObject {
    // Abstraction function:
    // AF（name）= 抽象数据类型的标记

    // Representation invariant:
    // 名字不能为空

    // Safety from rep exposure:
    // 设置属性name为private final

    /**
     * 继承构造方法
     * @param Name 名字
     */
    public PhysicalObject(String Name) {
        super(Name);
    }

    /**
     * 静态工厂方法
     * @param trackObjectName 轨道物体姓名
     * @return 返回轨道物体对象
     */
    public static PhysicalObject getInstance(String trackObjectName) {
        return new PhysicalObject(trackObjectName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PhysicalObject other = (PhysicalObject) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int compareTo(CommonObject o){
        PhysicalObject that = (PhysicalObject) o;
        return super.compareTo(that);
    }



}
