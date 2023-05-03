package applications.TrackGame;

import otherDirectory.CommonObject;
import physicalObject.PhysicalObject;

public class Athlete extends PhysicalObject {
    // Abstraction function:
    //AF(idNum)=id号码
    //AF(name)=名字
    //AF(nationality)=国籍
    //AF(age)=运动员年龄
    //AF(bestRecord)=最好成绩

    // Representation invariant:
    // 名字，id号码，国籍，年龄，最好成绩都不能为空

    // Safety from rep exposure:
    // 设置关键数据name,idNum,nationality,age,bestRecord为private final防止更改
    private final Integer idNum;
    private final String nationality;
    private final Integer age;
    private final double bestRecord;

    public Integer getIdNum() {
        return idNum;
    }

    public double getBestRecord() {
        return bestRecord;
    }

    public String getNationality() {
        return nationality;
    }

    public Integer getAge() {
        return age;
    }

    /**
     * 构造方法
     *
     * @param nameString  名字
     * @param idNum       id数
     * @param nationality 国籍
     * @param age         年龄
     * @param bestRecord  最佳纪录
     */
    public Athlete(String nameString, Integer idNum, String nationality, Integer age, double bestRecord) {
        super(nameString);
        this.idNum = idNum;
        this.nationality = nationality;
        this.age = age;
        this.bestRecord = bestRecord;
    }

    /**
     * 静态工厂方法
     *
     * @param nameString  名字
     * @param idNum       id号码
     * @param nationality 国籍
     * @param age         年龄
     * @param bestRecord  最好成绩
     * @return Athlete实例
     */
    public static Athlete getInstance(String nameString, Integer idNum, String nationality, Integer age,
                                      double bestRecord) {
        return new Athlete(nameString, idNum, nationality, age, bestRecord);
    }

    /**
     * 重写compareTo实现Comparable
     *
     * @param x 待比较运动员
     * @return 比较结果
     */
    @Override
    public int compareTo(CommonObject x) {
        Athlete that = (Athlete) x;
        if (this.bestRecord == that.getBestRecord()) {
            return 0;
        } else if (this.bestRecord > that.getBestRecord()) {
            return 1;
        } else {
            return -1;
        }
    }
}
