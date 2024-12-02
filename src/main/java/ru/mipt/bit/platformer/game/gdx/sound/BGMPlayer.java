package ru.mipt.bit.platformer.game.gdx.sound;

import com.badlogic.gdx.audio.Sound;
import ru.mipt.bit.platformer.game.gdx.utils.SoundUtils;

import java.util.Map;
import java.util.Set;

public class BGMPlayer {

    private static final String BGM_PATH = "sound/bgm";
    private final Map<String, Sound> bgmTracks;
    private final Sound currentBGM;

    public BGMPlayer() {
        this.bgmTracks = SoundUtils.loadFromDirectory(BGM_PATH);
        this.currentBGM = bgmTracks.values().iterator().next();
    }

    public void play() {
        currentBGM.play();
        currentBGM.loop();
    }
}
