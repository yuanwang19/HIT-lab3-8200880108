package applications.SocialNetworkCircle;

/**
 * 读取文件时用的辅助类
 */
public class relationKeeper {
    private final String toString;
    private final double weight;
    private final String fromString;


    /**
     * 构造方法
     *
     * @param fromString 来源字符串
     * @param toString   终点字符串
     * @param weight     权重
     */
    public relationKeeper(String fromString, String toString, double weight) {
        super();
        this.fromString = fromString;
        this.toString = toString;
        this.weight = weight;
    }



    public String getFromString() {
        return fromString;
    }

    public String getToString() {
        return toString;
    }

    public double getWeight() {
        return weight;
    }
}
