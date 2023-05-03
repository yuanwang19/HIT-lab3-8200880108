package track;

public class Track implements Comparable<Track>{
    // Abstraction function:
    // AF（name）= 轨道的标记
    // AF（radius）= 轨道的半径

    // Representation invariant:
    // 轨道半径不能为0，名字不能为null

    // Safety from rep exposure:
    // 设置属性name，radius为protected final
    // 在有必要的时候使用防御性拷贝

    protected final String name;
    protected final double radius;

    /**
     * 检查标记非空，半径大于0
     */
    private void checkRep() {
        assert this.name != null;
        assert this.radius >= 0;
    }

    /**
     * 静态工厂方法，构造名字为track，半径自定义的轨道
     *
     * @param radius 半径
     * @return 返回轨道对象
     */


    public static Track getInstance(String name, double radius) {
        return new Track(name, radius);
    }


    public static Track getInstance(double radius) {
        return new Track("track", radius);
    }
    /**
     * 构造方法
     *
     * @param name   标记
     * @param radius 半径
     */
    public Track(String name, double radius) {
        super(); //父类是object
        this.name = name;
        this.radius = radius;
        checkRep();
    }
    /**
     * 获得轨道name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获得轨道半径
     *
     * @return 轨道半径
     */
    public double getRadius() {
        return radius;
    }
    @Override
    public int compareTo(Track t) {
        return (this.radius - t.getRadius()) == 0 ? 0 : (this.radius - t.getRadius()) > 0 ? 1 : -1;
    }
}
