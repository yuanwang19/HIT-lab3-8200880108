package applications.SocialNetworkCircle;

import physicalObject.PhysicalObject;

/**
 * 一个{@code immutable}类型，表示具有姓名，性别，年龄的人
 */
public class Person extends PhysicalObject {
    // Abstraction function:
    // AF是从一个记录着名字，年龄，gender的抽象数据型到现实人际网络中的人的映射

    // Representation invariant:
    // 名字，年龄，gender不能为null

    // Safety from rep exposure:
    // 同父类
    // 设置关键数据age,gender为private final
    public String getGender() {
        return gender;
    }
    private final int age;
    private final String gender;

    public Integer getAge() {
        return age;
    }


    /**
     * @param name   姓名
     * @param age    年龄
     * @param gender 性别
     */
    public Person(String name, int age, String gender) {
        super(name);
        this.age = age;
        this.gender = gender;
    }

    /**
     * 静态工厂方法
     *
     * @param name   名字
     * @param age    年龄
     * @param gender 性别
     * @return 实例
     */
    public static Person getInstance(String name, int age, String gender) {
        return new Person(name, age, gender);
    }

}
