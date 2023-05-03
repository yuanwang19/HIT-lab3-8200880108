package circularOrbit;

import otherDirectory.Difference;
import track.Track;
import otherDirectory.Relation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ConcreteCircularOrbit <L,E> implements CircularOrbit<L,E>{
    //Abstraction Function:
    //ConcreteCircularOrbit是由多个轨道，多个轨道物体和中心物体组成的轨道系统
    //AF（centralObject）=轨道的中心点
    // AF（OrbitMap）= 轨道系统的轨道与该轨道上的物体的一对多关系
    // AF（centralRelationMap）= 轨道系统中心与其他物体的一对多关系
    // AF（trackRelationMap）= 轨道系统任一物体与其他物体的一对多关系

    // Representation invariant:
    // 两个物体之间只能存在一条关系
    // 轨道系统中的“关系”是一个非零的常数，可以为负数但不能为零。
    // 轨道不能重名，不能有轨道具有相同半径

    // Safety from rep exposure:
    // 设置了protected final属性
    // 要使用防御性拷贝

    protected L centralObject;  //中心点
    protected final Map<Track, List<E>> OrbitMap = new HashMap<>(); //轨道与该轨道上的物体的一对多关系
    protected final List<Relation<L, E>> centralRelationList = new ArrayList<>(); //轨道系统中心与其他物体的一对多关系
    protected final Map<E, List<Relation<E, E>>> trackRelationMap = new HashMap<>(); //轨道系统任一个物体与其他物体的一对多关系

    /**
     * checkRep()
     * 检查轨道不能重名，不能有轨道具有相同半径
     * 检查轨道系统中的“关系”是一个非零的常数，可以为负数但不能为零。
     * 检查两个物体之间只能存在一条关系
     */
    protected void checkRep(){
        // 遍历轨道系统的轨道，轨道不能重名，不能有相同的半径
        for (Track t : OrbitMap.keySet()) {
            for (Track track : OrbitMap.keySet()) {
                if (!t.equals(track)) {
                    assert !track.getName().equals(t.getName());
                    assert t.getRadius() != track.getRadius();
                }
            }
        }

// 遍历中心点的物体和其他物体之间的“关系”集合，不存在为零的“关系”
        for (Relation<L, E> centralRelation : centralRelationList) {
            assert centralRelation.getWeight() != 0;
        }

// 遍历处于同一轨道的多个物体之间、处于不同轨道的多个物体之间的“关系”集合，不存在为零的“关系”
        for (E obj1 : trackRelationMap.keySet()) {
            for (Relation<E, E> trackRelation : trackRelationMap.get(obj1)) {
                assert trackRelation.getWeight() != 0;
            }
        }
    }
    /**
     * 设置轨道的中心物体
     * 轨道系统中中只能设置一个
     *
     * @param centralObject 中心物体
     */
    @Override
    public void setCentralObject(L centralObject)
    {
        this.centralObject=centralObject;
        checkRep();
    }
    /**
     * 增加一条轨道
     *
     * @param t 添加的轨道
     * @return 添加成功返回true 添加失败返回false
     */
    public boolean addTrack(Track t)
    {
        if (OrbitMap.containsKey(t)) {
            System.out.println("该轨道已存在,添加失败");
            return false;
        }
        else {
            OrbitMap.put(t, new ArrayList<>());
            checkRep();
            return true;
        }
    }

    /**
     * get轨道中心物体
     *
     * @return 轨道的中心物体
     */
    @Override
    public L getCentralObject()
    {
        return centralObject;
    }

    /**
     * 删除一条轨道
     *
     * @param t 所需删除的轨道
     * @return 删除成功返回true 删除失败返回false
     */
    @Override
    public boolean removeTrack(Track t) {
        if (!OrbitMap.containsKey(t))
        {
            System.out.println("该轨道不存在！");
            return false;
        }
        else
        {
            OrbitMap.remove(t);
            System.out.println("删除成功");
            checkRep();
            return true;
        }
    }

    /**
     * 返回轨道的总数量
     *
     * @return 轨道的总数量
     */
    @Override
    public Integer getTrackNum() {
        return OrbitMap.keySet().size();
    }

    /**
     * 得到某一条轨道上有多少物体
     *
     * @param t 某一条轨道
     * @return 该轨道上的物体数量
     */

    @Override
    public Integer getTrackObjectNum(Track t) {
        if (!OrbitMap.containsKey(t)) {
            System.out.println("该轨道不存在！");
            return -1;
        }
        checkRep();
        return OrbitMap.get(t).size();
    }

    /**
     * 返回一条轨道的所有物体
     *
     * @param t 轨道
     * @return 轨道上的物体的list
     */
    @Override
    public List<E> getObjectOnTrack(Track t) {
        if (!OrbitMap.containsKey(t)) {
            System.out.println("该轨道不存在！");
            return null;
        }
        return OrbitMap.get(t);
    }

    /**
     * 添加一个物体到某个轨道上
     *
     * @param t 某一条轨道
     * @param object 所需添加的物体
     * @return 添加成功返回true 否则false
     */
    @Override
    public boolean addObjectToTrack(Track t, E object) {
        if (!OrbitMap.containsKey(t)) {
            return false;
        }

        for (E x : OrbitMap.get(t)) {
            if (x.equals(object)) {
                return false;
            }
        }
        OrbitMap.get(t).add(object);
        checkRep();
        return true;
    }

    /**
     * 从某个轨道上删除一个物体
     *
     * @param t 某一条轨道
     * @param object 需删除的物体
     * @return 删除成功返回true 删除失败返回false
     */
    @Override
    public boolean removeObjectOnTrack(Track t, E object) {
        if (!OrbitMap.containsKey(t)) {
            System.out.println("该轨道不存在！");
            return false;
        }

        for (E x : OrbitMap.get(t)) {
            if (x.equals(object)) {
                OrbitMap.get(t).remove(object);
                checkRep();
                return true;
            }
        }
        System.out.println("不存在该物体");
        return false;
    }
    /**
     * 为两个轨道系统上的物体添加关系（距离）
     *
     * @param object1 物体1
     * @param object2 物体2
     * @param weight 两个物体之间的关系距离
     * @return 关系添加成功返回true 否则返回false
     */

    @Override
    public boolean addtrackRelation(E object1, E object2, double weight)
    {
        if (!contains(object1)) {
            System.out.println("物体1在轨道系统中不存在！");
            return false;
        } else if (!contains(object2)) {
            System.out.println("物体2在轨道系统中不存在！");
            return false;
        }

        if (object1.equals(object2)) {
            System.out.println("两个物体相同！");
            return false;
        }
        if (weight == 0) {//若关系权重为0，打印错误信息并返回false
            System.out.println("关系权重不能为0");
            return false;
        }

        if (!trackRelationMap.containsKey(object1)) {
            trackRelationMap.put(object1, new ArrayList<>());
            checkRep();
        }
        if (!trackRelationMap.containsKey(object2)) {
            trackRelationMap.put(object2, new ArrayList<>());
            checkRep();
        }
        //如果关系集合没有物体1或物体2，加入
        trackRelationMap.get(object1).add(new Relation<>(object1, object2, weight));
        checkRep();
        System.out.println("添加关系成功");
        return true;

    }

    /**
     * 为轨道系统的中心物体和某个轨道上的物体添加关系（距离）
     *
     * @param object 物体
     * @param weight 该物体与中心物体间的关系距离
     * @return 关系添加成功返回true 否则false
     */
    @Override
    public boolean addCentralRelation(E object, double weight)
    {
        if (!contains(object)) {
            System.out.println("物体在轨道系统中不存在！");
            return false;
        }

        if (weight == 0) {
            System.out.println("关系权重不能为0");
            return false;
        }
        if (centralRelationList.contains(new Relation<>(this.centralObject, object, weight))) {
            System.out.println("中心物体与该物体已经存在关系！");
            return false;
        } else {
            centralRelationList.add(new Relation<>(this.centralObject, object, weight));
            checkRep();
            return true;
        }
    }
    /**
     * 删除轨道系统两个物体的关系
     *
     * @param object1 物体1
     * @param object2 物体2
     * @return 关系删除成功返回true 否则返回false
     */
    @Override
    public boolean removetrackRelation(E object1, E object2) {
        if (!trackRelationMap.containsKey(object1) || !trackRelationMap.containsKey(object2)) {
            System.out.println("若关系集合中不包含object1和object2");
            return false;
        }
        List<Relation<E, E>> object1Relations = trackRelationMap.get(object1);
        List<Object> object1RelationObjects = new ArrayList<>();
        for (Relation<E, E> relation : object1Relations) {
            object1RelationObjects.add(relation.getObject2());
        }

        List<Relation<E, E>> object2Relations = trackRelationMap.get(object2);
        List<Object> object2RelationObjects = new ArrayList<>();
        for (Relation<E, E> relation : object2Relations) {
            object2RelationObjects.add(relation.getObject2());
        }

        if (!object1RelationObjects.contains(object2) && !object2RelationObjects.contains(object1)) {
            System.out.println("两物体之间未建立关系！");
            return false;
        } else {
            if (object1RelationObjects.contains(object2)) {
                for (Iterator<Relation<E, E>> iter = object1Relations.iterator(); iter.hasNext();) {
                    Relation<E, E> rel = iter.next();
                    if (rel.getObject2().equals(object2)) {
                        iter.remove();
                        break;
                    }
                }
            } else {
                for (Iterator<Relation<E, E>> iter = object2Relations.iterator(); iter.hasNext();) {
                    Relation<E, E> rel = iter.next();
                    if (rel.getObject2().equals(object1)) {
                        iter.remove();
                        break;
                    }
                }
            }
            checkRep();
            return true;
        }
    }
    /**
     * 为轨道系统的中心物体和某个轨道上的物体删除关系
     *
     * @param object 物体
     * @return 关系删除成功返回true 否则返回false
     */

    @Override
    public boolean removeCentralRelation(E object){
        List<Object> object2List = new ArrayList<>();
        for (Relation<L, E> relation : centralRelationList) {
            object2List.add(relation.getObject2());
        }

        if (!object2List.contains(object)) {
            System.out.println("中心物体与该物体之间未建立关系！");
            return false;
        } else {
            for (Relation<L, E> relation : centralRelationList) {
                if (relation.getObject2().equals(object)) {
                    centralRelationList.remove(relation);
                    break;
                }
            }
            return true;
        }

    }
    /**
     * 将 oldObj 从当前所在轨道 迁移到 t
     * 因为PhysicalObject是Immutable的，所以需要新建对象，然后放入新的轨道t
     *
     * @param oldObj 旧对象
     * @param newObj 新对象
     * @param t      轨道
     * @return 转移成功或者失败
     */
    @Override
    public boolean transit(E oldObj, E newObj, Track t) {
        if (!contains(oldObj)) {
            System.out.println("轨道系统中不存在该物体！");
            return false;
        } else {
            if (!OrbitMap.containsKey(t)) {
                System.out.println("该轨道不存在！");
                return false;
            } else {
                removeObjectOnTrack(getTrackOfObject(oldObj), oldObj);
                addObjectToTrack(t, newObj);
                checkRep();
                return true;
            }
        }
    }
    /**
     * 将 object 从当前位置移动到新的角度所对应的位置
     * 因为PhysicalObject是Immutable的，所以需要新建对象，然后放入新的轨道t
     *
     * @param oldObject 旧对象
     * @param newObject 新对象
     * @return 移动成功还是失败
     */
    @Override
    public boolean move(E oldObject, E newObject) {
        if (!contains(oldObject)) {
            System.out.println("待移动物体不存在！");
            return false;
        } else {
            Track track = getTrackOfObject(oldObject);
            removeObjectOnTrack(track, oldObject);
            addObjectToTrack(track, newObject);
            checkRep();
            return true;
        }
    }
    /**
     * 计算该轨道系统的信息熵
     *
     * @return 轨道系统信息熵
     */

    @Override
    public double calculateEntropyOfOrbit() {
        double entropy = 0.0;
        int totalNum = 0;
        List<Integer> orbitNumList = new ArrayList<>();

        for (List<E> orbit : OrbitMap.values()) {
            int orbitNum = orbit.size();
            orbitNumList.add(orbitNum);
            totalNum += orbitNum;
        }

        for (int orbitNum : orbitNumList) {
            if (orbitNum != 0) {
                double prob = (double)orbitNum / totalNum;
                entropy -= prob * Math.log(prob) / Math.log(2);
            }
        }

        return entropy;
    }

    /**
     * 获得两物体间逻辑距离
     *
     * @param object1 物体1
     * @param object2 物体2
     * @return 逻辑距离
     */

    public Integer getLogicalDistance(E object1, E object2) {
        if (object1 == object2) {
            return 0;
        }

        Queue<E> queue = new LinkedList<>();
        Map<E, Integer> distantMap = new HashMap<>();

        queue.offer(object1);
        distantMap.put(object1, 0);

        while (!queue.isEmpty()) {
            E topObject = queue.poll();
            int temp = distantMap.get(topObject);
            Set<E> friendList = getTrackConnectedObject(topObject);

            for (E friend : friendList) {
                if (!distantMap.containsKey(friend)) {
                    distantMap.put(friend, temp + 1);
                    queue.offer(friend);

                    if (friend == object2) {
                        return distantMap.get(object2);
                    }
                }
            }
        }

        return -1;

    }

    /**
     * 判断当前轨道系统是否包含某个物体
     *
     * @param object 需判断的物体
     * @return 包含返回true 否则false
     */
    @Override
    public boolean contains(E object) {
        for (Track t : OrbitMap.keySet()) {//遍历轨道
            if (OrbitMap.get(t).contains(object)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 返回某个物体object所在的轨道对象Track
     *
     * @param object 某个物体
     * @return 所在轨道
     *
     */
    @Override
    public Track getTrackOfObject(E object) {
        for (Track t : OrbitMap.keySet()) {//遍历轨道，返回所需的轨道对象
            if (OrbitMap.get(t).contains(object)) {
                checkRep();
                return t;
            }
        }
        return null;
    }
    /**
     * 返回与中心连接的物体
     *
     * @return 物体构成的set
     *
     */
    @Override
    public Set<E> getCentralConnectedObject() {
        Set<E> objectSet = new HashSet<>();
        for (Relation<L, E> relation : centralRelationList) {
            objectSet.add(relation.getObject2());
        }
        checkRep();
        return objectSet;
    }
    /**
     * 返回与某个轨道物体连接的所有物体
     * @param object 某个物体
     * @return 物体构成的集合
     */
    @Override
    public Set<E> getTrackConnectedObject(E object) {
        Set<E> objectSet = new HashSet<>();
        if (!contains(object)) {
            System.out.println("该物体不存在！");
            return null;
        }
        for (Relation<E, E> relation : trackRelationMap.getOrDefault(object, Collections.emptyList())) {
            objectSet.add(relation.getObject2());
        }
        for (E key : trackRelationMap.keySet()) {
            if (!key.equals(object)) {
                for (Relation<E, E> relation : trackRelationMap.get(key)) {
                    objectSet.add(relation.getObject2());
                }
            }
        }
        checkRep();
        return objectSet;

    }

    /**
     * 比较当前轨道系统和轨道系统c
     * 并返回两者的不同
     *
     * @param that 需比较的轨道系统
     * @return 两者的不同
     */
    @Override
    public Difference getDifference(CircularOrbit<L, E> that) {
        int trackNumDiff;
        List<Integer> numDiff = new ArrayList<>();
        List<List<Set<String>>> objectDiff = new ArrayList<>();

        trackNumDiff = this.getTrackNum() - that.getTrackNum();
        List<Track> sortedTracks1 = this.getSortedTracks();
        List<Track> sortedTracks2 = that.getSortedTracks();

        for (int i = 0; i < Math.min(sortedTracks1.size(), sortedTracks2.size()); i++) {
            objectDiff.add(new ArrayList<>());
            objectDiff.get(i).add(new HashSet<>());
            objectDiff.get(i).add(new HashSet<>());

            int numObjDiff = this.getTrackObjectNum(sortedTracks1.get(i)) - that.getTrackObjectNum(sortedTracks2.get(i));
            numDiff.add(numObjDiff);

            for (E currentObj : this.getObjectOnTrack(sortedTracks1.get(i))) {
                if (!that.getObjectOnTrack(sortedTracks2.get(i)).contains(currentObj)) {
                    objectDiff.get(i).get(0).add(currentObj.toString());
                }
            }
            for (E currentObj : that.getObjectOnTrack(sortedTracks2.get(i))) {
                if (!this.getObjectOnTrack(sortedTracks1.get(i)).contains(currentObj)) {
                    objectDiff.get(i).get(1).add(currentObj.toString());
                }
            }
        }

        for (int i = sortedTracks2.size(); i < sortedTracks1.size(); i++) {
            numDiff.add(this.getTrackObjectNum(sortedTracks1.get(i)));

            objectDiff.add(new ArrayList<>());
            objectDiff.get(i).add(new HashSet<>());
            objectDiff.get(i).add(new HashSet<>());

            for (E currentObj : this.getObjectOnTrack(sortedTracks1.get(i))) {
                objectDiff.get(i).get(0).add(currentObj.toString());
            }
        }

        for (int i = sortedTracks1.size(); i < sortedTracks2.size(); i++) {
            numDiff.add(-that.getTrackObjectNum(sortedTracks2.get(i)));

            objectDiff.add(new ArrayList<>());
            objectDiff.get(i).add(new HashSet<>());
            objectDiff.get(i).add(new HashSet<>());

            for (E currentObj : that.getObjectOnTrack(sortedTracks2.get(i))) {
                objectDiff.get(i).get(1).add(currentObj.toString());
            }
        }

        checkRep();
        return new Difference(trackNumDiff, numDiff, objectDiff);

    }
    /**
     * 获得当前轨道系统包含的所有轨道的按半径排列成的list
     *
     * @return 半径有序的Track链表
     */
    @Override
    public List<Track> getSortedTracks() {
        List<Track> listTracks = new ArrayList<>(OrbitMap.keySet());
        Collections.sort(listTracks);//比较半径（要继承Comparable<Track>）
        checkRep();
        return listTracks;
    }
    /**
     * 将轨道可视化
     */
    @Override
    public void drawPicture() {
        Color coral = new Color(255, 127, 80);
        Color lavender = new Color(230, 130, 250);
        Color forestGreen = new Color(34, 139, 34);
        JFrame frame = new JFrame("可视化");
        int WIDTH = 1000;
        int HEIGHT = 1000;
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jpanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                for (Track t : OrbitMap.keySet()) {
                    int R = (int) t.getRadius();
                    graphics.drawOval(400 - (int) (0.5 * R), 400 - (int) (0.5 * R), R, R);
                    int objectNum = getTrackObjectNum(t);
                    double angle = objectNum == 0 ? 0 : 360.0 / objectNum;
                    int i = 1;
                    for (E p : getObjectOnTrack(t)) {
                        int x = (int) (400 + 0.5 * R * Math.cos(Math.PI * i * angle / 180));
                        int y = (int) (400 + 0.5 * R * Math.sin(Math.PI * i * angle / 180));
                        graphics.setColor(coral);
                        graphics.drawOval(x - 5, y - 5, 10, 10);
                        graphics.fillOval(x - 5, y - 5, 10, 10);
                        graphics.drawString(p.toString(), x + 5, y);
                        i++;
                        graphics.setColor(Color.BLACK);
                    }
                }
                graphics.setColor(forestGreen);
                graphics.drawOval(400 - 10, 400 - 10, 20, 20);
                graphics.fillOval(400 - 10, 400 - 10, 20, 20);
            }
        };
        frame.add(jpanel);
        frame.setVisible(true);
    }
    /**
     * 判断系统的合法性(主要用于继承给子类)
     * @return 是否合法
     */
    @Override
    public boolean checkOrbitAvailable(){
        return true;
    }

    /**
     * 迭代器实现
     */
    @Override
    public Iterator<E> iterator() {
        return new MyIterator(OrbitMap);
    }

    /**
     * 内部类，生成迭代器，遍历轨道系统内所有的轨道物体，顺序为顺序为从内轨道逐步向外，
     * 同一轨道物体按名称排序
     */
    private static class MyIterator<E extends Comparable<E>> implements Iterator<E> {
        private final List<E> ObjectList;
        private int index;
        private int size;

        /**
         * 构造方法，初始化 ObjectList 作为 OrbitIterator 的迭代输出
         *
         * @param orbitMap 迭代对象
         */
        public MyIterator(Map<Track, List<E>> orbitMap) {
            index = 0;
            size = orbitMap.values().stream().mapToInt(List::size).sum();
            ObjectList = orbitMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .flatMap(entry -> entry.getValue().stream().sorted())
                    .collect(Collectors.toList());
        }

        /**
         * 判断是否有下一个
         *
         * @return 是返回 true
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * 获取下一个迭代对象的方法
         *
         * @return 下一个 E 对象
         */
        @Override
        public E next() {
            return ObjectList.get(index++);
        }
    }



}

