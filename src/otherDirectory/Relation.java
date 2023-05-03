package otherDirectory;

public class Relation<L, E> {


    // Abstraction function:
    //   AF(object1) = 源点
    //   AF(object2) = 终点
    //   AF(weight) = 权重
    // Representation invariant:
    //   weight != 0
    //   source和target不为空
    // Safety from rep exposure:
    //   将属性值设置为private final

    private final L object1;
    private final E object2;
    private final double weight;

    /**
     * 检验RI，权重非零且边的两点不为空
     */
    public void checkRep() {
        assert object1 != null;
        assert object2 != null;
        assert weight != 0;
    }

    /**
     * 构造方法
     *
     * @param object1 关系的一个点
     * @param object2 关系的另外一个点
     * @param weight  关系的权值
     */
    public Relation(L object1, E object2, double weight) {
        this.object1 = object1;
        this.object2 = object2;
        this.weight = weight;
        checkRep();
    }
    /**
     * 得到关系的源点
     *
     * @return 关系的物体1
     */
    public L getObject1() {
        return object1;
    }

    /**
     * 得到关系的终点
     *
     * @return 关系的物体2
     */
    public E getObject2() {
        return object2;
    }

    /**
     * 得到关系的权重
     *
     * @return 关系的权重
     */
    public double getWeight() {
        return weight;
    }

    /**
     * 比较两对关系是否相同
     *
     * @param relation2 需比较的关系
     * @return 相同返回true不同返回false
     */
    @Override
    public boolean equals(Object relation2) {
        Relation<L, E> that = (Relation<L, E>) relation2;
        checkRep();
        return this.object1.equals(that.object1) && this.object2.equals(that.object2) && this.weight == that.weight;
    }

}
