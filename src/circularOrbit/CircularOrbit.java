package circularOrbit;

import otherDirectory.Difference;
import track.Track;
import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface CircularOrbit<L, E> {
    // Abstraction function:
    // AF是CircularOrbit<L, E>到真实的轨道结构的映射

    // Representation invariant:
    // 不同的轨道名字不可以相同
    // 轨道半径不能一样

    // Safety from rep exposure:
    // 属性设置为private final
    // 需要使用防御性拷贝

    /**
     * 设置轨道的中心物体
     * 轨道系统中中只能设置一个
     *
     * @param centralObject 中心物体
     */
    public void setCentralObject(L centralObject);
    /**
     * 增加一条轨道
     *
     * @param t 添加的轨道
     * @return 添加成功返回true 添加失败返回false
     */
    public boolean addTrack(Track t);


    /**
     * get轨道中心物体
     *
     * @return 轨道的中心物体
     */
    public L getCentralObject();


    /**
     * 删除一条轨道
     *
     * @param t 所需删除的轨道
     * @return 删除成功返回true 删除失败返回false
     */
    public boolean removeTrack(Track t);

    /**
     * 返回轨道的总数量
     *
     * @return 轨道的总数量
     */
    public Integer getTrackNum();

    /**
     * 得到某一条轨道上有多少物体
     *
     * @param t 某一条轨道
     * @return 该轨道上的物体数量
     */
    public Integer getTrackObjectNum(Track t);

    public List<E> getObjectOnTrack(Track t);

    /**
     * 添加一个物体到某个轨道上
     *
     * @param t 某一条轨道
     * @param object 所需添加的物体
     * @return 添加成功返回true 否则false
     */
    public boolean addObjectToTrack(Track t, E object);

    /**
     * 从某个轨道上删除一个物体
     *
     * @param t 某一条轨道
     * @param object 需删除的物体
     * @return 删除成功返回true 删除失败返回false
     */
    public boolean removeObjectOnTrack(Track t, E object);

    /**
     * 为两个轨道系统上的物体添加关系（距离）
     *
     * @param object1 物体1
     * @param object2 物体2
     * @param weight 两个物体之间的关系距离
     * @return 关系添加成功返回true 否则返回false
     */
    public boolean addtrackRelation(E object1, E object2, double weight);

    /**
     * 为轨道系统的中心物体和某个轨道上的物体添加关系（距离）
     *
     * @param object 物体
     * @param weight 该物体与中心物体间的关系距离
     * @return 关系添加成功返回true 否则false
     */
    public boolean addCentralRelation(E object, double weight);

    /**
     * 删除轨道系统两个物体的关系
     *
     * @param object1 物体1
     * @param object2 物体2
     * @return 关系删除成功返回true 否则返回false
     */
    public boolean removetrackRelation(E object1, E object2);

    /**
     * 为轨道系统的中心物体和某个轨道上的物体删除关系
     *
     * @param object 物体
     * @return 关系删除成功返回true 否则返回false
     */
    public boolean removeCentralRelation(E object);

    /**
     * 将 oldObj 从当前所在轨道 迁移到 t
     * 因为PhysicalObject是Immutable的，所以需要新建对象，然后放入新的轨道t
     *
     * @param oldObj 旧对象
     * @param newObj 新对象
     * @param t      轨道
     * @return 转移成功或者失败
     */
    boolean transit(E oldObj, E newObj, Track t);

    /**
     * 将 object 从当前位置移动到新的角度所对应的位置
     * 因为PhysicalObject是Immutable的，所以需要新建对象，然后放入新的轨道t
     *
     * @param oldObject 旧对象
     * @param newObject 新对象
     * @return 移动成功还是失败
     */
    boolean move(E oldObject, E newObject);

    /**
     * 计算该轨道系统的信息熵
     *
     * @return 轨道系统的信息熵
     */
    public double calculateEntropyOfOrbit();

    /**
     * 获得两物体间逻辑距离
     *
     * @param object1 物体1
     * @param object2 物体2
     * @return 逻辑距离
     */
    public Integer getLogicalDistance(E object1, E object2);

    /**
     * 判断当前轨道系统是否包含某个物体
     *
     * @param object 需判断的物体
     * @return 包含返回true 否则false
     */
    public boolean contains(E object);

    /**
     * 返回某个物体object所在的轨道对象Track
     *
     * @param object 某个物体
     * @return 所在轨道
     *
     */
    public Track getTrackOfObject(E object);

    /**
     * 返回与中心连接的物体
     *
     * @return 物体构成的set
     *
     */
    public Set<E> getCentralConnectedObject();

    /**
     * 返回与某个轨道物体连接的所有物体
     * @param object 某个物体
     * @return 物体构成的集合
     */
    public Set<E> getTrackConnectedObject(E object);

    /**
     * 比较当前轨道系统和轨道系统c
     * 并返回两者的不同
     *
     * @param that 需比较的轨道系统
     * @return 两者的不同
     */
    public Difference getDifference(CircularOrbit<L, E> that);

    /**
     * 获得当前轨道系统包含的所有轨道的按半径排列成的list
     *
     * @return 半径有序的Track链表
     */
    public List<Track> getSortedTracks();

    /**
     * 将轨道可视化
     */
    public void drawPicture();

    boolean checkOrbitAvailable();

    /**
     * 迭代器实现
     *
     * @return 返回轨道的迭代器
     */
    Iterator<E> iterator();
}
