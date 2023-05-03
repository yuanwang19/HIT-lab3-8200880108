package applications.TrackGame.Strategy;

import applications.TrackGame.Athlete;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface Strategy {
    /**
     * 排序策略
     *
     * @param runnerlist 运动员列表
     * @param tracklist  赛道列表
     * @return 排序策略Map
     */
    List<Map<Track, List<Athlete>>> Arrange(List<Athlete> runnerlist, List<Track> tracklist);
}
