package applications.SocialNetworkCircle;

import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.Relation;
import track.Track;
import otherDirectory.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
public class SocialNetCircularOrbit extends ConcreteCircularOrbit<Person, Person> {
    // Abstraction function:
    // SocialNetCircularOrbit是一个由多个Track，多个朋友和中心用户组成的对人际关系轨道结构的抽象

    // Representation invariant:
    // 轨道不能重名，不能具有相同半径

    // Safety from rep exposure:
    // 同父类
    // 使用防御性拷贝
    public SocialNetCircularOrbit() {
        super();
    }
    /**
     * 重写添加中心用户与轨道上的人员关系的方法
     */
    @Override
    public boolean addCentralRelation(Person person, double weight) {
        if (weight == 0) { removeCentralRelation(person); return true; }
        if (centralRelationList.contains(new Relation<>(this.centralObject, person, weight))) { System.out.println("中心物体与该物体之间已建立关系！"); return false; }
        centralRelationList.add(new Relation<>(this.centralObject, person, weight));
        checkRep();
        return true;

    }
    /**
     * 重写添加轨道上的人员之间关系的方法
     */
    @Override
    public boolean addtrackRelation(Person object1, Person object2, double weight) {
        if (!trackRelationMap.containsKey(object1)) {
// 若关系集合中不包含object1，则添加object1
            trackRelationMap.put(object1, new ArrayList<>());
        }

        if (!trackRelationMap.containsKey(object2)) {
// 若关系集合中不包含object2，则添加object2
            trackRelationMap.put(object2, new ArrayList<>());
        }

        if (weight == 0) {
// 若关系权重为0，在集合中删除关系
            removetrackRelation(object1, object2);
            return true;
        } else if (trackRelationMap.get(object1).contains(new Relation<>(object1, object2, weight))
                || trackRelationMap.get(object2).contains(new Relation<>(object2, object1, weight))) {
// 若关系集合中已存在该关系，则返回false
            System.out.println("两物体之间已建立关系！");
            return false;
        } else {
// 否则，添加关系并返回true
            trackRelationMap.get(object1).add(new Relation<>(object1, object2, weight));
// checkRep();
            return true;
        }
    }

        @Override
    public Set<Person> getTrackConnectedObject(Person object) {
            Set<Person> objectSet = new HashSet<>();

            for (Person obj : trackRelationMap.keySet()) {
                if (!obj.equals(object)) {
                    for (Relation<Person, Person> relation : trackRelationMap.get(obj)) {
                        if (relation.getObject2().equals(object)) {
                            objectSet.add(relation.getObject1());
                        }
                    }
                } else {
                    for (Relation<Person, Person> relation : trackRelationMap.get(obj)) {
                        objectSet.add(relation.getObject2());
                    }
                }
            }
            checkRep();
            return objectSet;
    }
    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getCentralObject().getName())
                .append("的关系网:\n");

        List<Track> trackList = this.getSortedTracks();
        for (Track currentTrack : trackList) {
            sb.append(currentTrack.getName()).append("上有：");

            for (Person a : OrbitMap.get(currentTrack)) {
                sb.append(a.getName()).append("\t");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 重写drawPicture方法，可视化可以画关系了
     */
    @Override
    public void drawPicture() {
        Color coral = new Color(255, 127, 80);
        Color lavender = new Color(230, 210, 250);
        Color forestGreen = new Color(34, 139, 34);
        JFrame frame = new JFrame();
        String TITLE = "可视化";
        int WIDTH = 800;
        int HEIGHT = 800;

        JPanel jpanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics graphics) {


                super.paint(graphics);
                Map<Person, Position> positionMap = new HashMap<>();

                OrbitMap.keySet().stream().forEach(t -> {
                    int R = (int) t.getRadius() * 3;
                    graphics.drawOval(400 - (int) (0.5 * R), 400 - (int) (0.5 * R), R, R);
                    int objectNum = getTrackObjectNum(t);
                    double angle = objectNum == 0 ? 0 : 360.0 / objectNum;
                    final int[] i = {1};

                    getObjectOnTrack(t).stream().forEach(p -> {
                        int x = (int) (400 + 0.5 * R * Math.cos(Math.PI * i[0] * angle / 180));
                        int y = (int) (400 + 0.5 * R * Math.sin(Math.PI * i[0] * angle / 180));
                        positionMap.put(p, new Position(x, y));

                        graphics.setColor(coral);
                        graphics.drawOval(x - 5, y - 5, 10, 10);
                        graphics.fillOval(x - 5, y - 5, 10, 10);
                        graphics.drawString(p.getName(), x + 5, y);
                        i[0]++;
                        graphics.setColor(Color.BLACK);
                    });
                });

                graphics.setColor(lavender);
                graphics.drawOval(400 - 10, 400 - 10, 20, 20);
                graphics.fillOval(400 - 10, 400 - 10, 20, 20);
                graphics.setColor(forestGreen);

                getCentralConnectedObject().stream().forEach(p -> graphics.drawLine(400, 400, positionMap.get(p).getX(), positionMap.get(p).getY()));

                getSortedTracks().stream().forEach(t -> getObjectOnTrack(t).stream().forEach(start -> getTrackConnectedObject(start).stream().forEach(end -> graphics.drawLine(positionMap.get(start).getX(), positionMap.get(start).getY(),
                        positionMap.get(end).getX(), positionMap.get(end).getY()))));
            }
        };

        frame.add(jpanel);
        frame.setTitle(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }

    public void rearrangeOrbits() {
        Map<Track, List<Person>> orbitMap = new HashMap<>();
        List<Track> trackList = new ArrayList<>();
        Set<Person> processedPeople = new HashSet<>();

        Track initialTrack = new Track("track0", 50);
        orbitMap.put(initialTrack, new ArrayList<>());
        trackList.add(initialTrack);

        for (Person person : this.getCentralConnectedObject()) {
            orbitMap.get(initialTrack).add(person);
            processedPeople.add(person);
        }

        int i = 0;
        boolean flag = true;

        while (flag) {
            flag = false;
            i++;
            Track newTrack = new Track("track" + i, 50 + 100 * i);
            Set<Person> newPeopleSet = new HashSet<>();

            for (Person person : processedPeople) {
                if (this.getTrackConnectedObject(person).size() > 0) {
                    for (Person relatedPerson : trackRelationMap.get(person).stream()
                            .map(Relation::getObject2)
                            .collect(Collectors.toList())) {
                        if (!processedPeople.contains(relatedPerson)) {
                            newPeopleSet.add(relatedPerson);
                            flag = true;
                        }
                    }
                }
            }

            if (flag) {
                trackList.add(newTrack);
                orbitMap.put(newTrack, new ArrayList<>());
                orbitMap.get(newTrack).addAll(newPeopleSet);
                processedPeople.addAll(newPeopleSet);
            }
        }

        OrbitMap.clear();
        for (Track track : trackList) {
            this.addTrack(track);
        }

        for (Map.Entry<Track, List<Person>> entry : orbitMap.entrySet()) {
            for (Person person : entry.getValue()) {
                this.addObjectToTrack(entry.getKey(), person);
            }
        }
    }
    /**

     计算亲密度

     @param p 轨道上的人

     @return 亲密度
     */
    public double computeIntimacy(Person p) {
        if (!contains(p)) {
            System.out.println("该轨道上不存在该人");
            return 0;
        }

        double minLogicalDistance = Double.POSITIVE_INFINITY;
        for (Person person : getCentralConnectedObject()) {
            double distance = computeLogicalDistance(person, p);
            if (distance < minLogicalDistance) {
                minLogicalDistance = distance;
            }
        }
        return (minLogicalDistance == Double.POSITIVE_INFINITY) ? 0.0 : (minLogicalDistance + 1.0);
    }

    /**

     计算两个人之间的逻辑距离
     @param p1 第一个人
     @param p2 第二个人
     @return 逻辑距离
     */
    private double computeLogicalDistance(Person p1, Person p2) {
        if (p1.equals(p2)) {
            return 0.0;
        }
        Map<Person, Double> distances = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        distances.put(p1, 0.0);
        queue.offer(p1);
        while (!queue.isEmpty()) {
            Person current = queue.poll();
            double currentDistance = distances.get(current);
            Set<Person> neighbors = getTrackConnectedObject(current);
            for (Person neighbor : neighbors) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, currentDistance + 1.0);
                    queue.offer(neighbor);
                }
            }
            if (current.equals(p2)) {
                return distances.get(p2);
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * 验证轨道系统合法性
     *
     * @return 是否合法
     */
    @Override
    public boolean checkOrbitAvailable() {
        List<Track> trackList = this.getSortedTracks();
        double dist;

        for (List<Person> friendList : OrbitMap.values()) {
            for (Person friend : friendList) {
                dist = computeIntimacy(friend);
                if (dist < 0) {
                    return false;
                } else if (trackList.get((int) dist - 1) != getTrackOfObject(friend)) {
                    return false;
                }
            }
        }
        return true;
    }

}
