package me.jumper251.replay.filesystem.saving;

import me.jumper251.replay.replaysystem.Replay;
import me.jumper251.replay.utils.fetcher.Consumer;

import java.util.List;

public interface IReplaySaver {

    void saveReplay(Replay replay);

    void loadReplay(String replayName, Consumer<Replay> consumer);

    boolean replayExists(String replayName);

    void deleteReplay(String replayName);

    List<String> getReplays();
}
