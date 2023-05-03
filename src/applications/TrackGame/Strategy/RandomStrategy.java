package applications.TrackGame.Strategy;

import applications.TrackGame.Athlete;
import track.Track;

import java.util.*;

/**
 * 随机分组策略
 */
public class RandomStrategy implements Strategy {

    /**
     * 排序策略
     *
     * @param athleteList 运动员列表
     * @param trackList  赛道列表
     * @return 排序策略Map
     */
    @Override
    public List<Map<Track, List<Athlete>>> Arrange(List<Athlete> athleteList, List<Track> trackList) {
        List<Map<Track, List<Athlete>>> arrangementMapList = new LinkedList<>();
        int orbitNum = (int) Math.ceil((double) athleteList.size() / trackList.size());

        for (int i = 0; i < orbitNum; i++) {
            Map<Track, List<Athlete>> currentMap = new HashMap<>();
            for (Track track : trackList) {
                currentMap.put(track, new LinkedList<>());
            }
            arrangementMapList.add(currentMap);
        }

        Random random = new Random();
        int j = 0;
        for (Athlete athlete : athleteList) {
            Track track = trackList.get(j % trackList.size());
            Map<Track, List<Athlete>> currentMap = arrangementMapList.get(j / trackList.size());
            currentMap.get(track).add(athlete);
            j++;
        }

        return arrangementMapList;
    }



}
