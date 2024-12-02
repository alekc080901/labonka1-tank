package ru.mipt.bit.platformer.game.gdx.sound;

import com.badlogic.gdx.audio.Sound;
import ru.mipt.bit.platformer.game.core.entity.AbstractSound;
import ru.mipt.bit.platformer.game.gdx.utils.SoundUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SFXPlayer {

    private static final String SFX_PATH = "sound/sfx";
    private final Map<String, Sound> sfxSounds;
    private final Map<AbstractSound, Runnable> soundExecutionMethod = new HashMap<>();

//    static {
//        soundExecutionMethod.put(AbstractSound.SHOOT_SOUND, )
//    }
    public SFXPlayer() {
        this.sfxSounds = SoundUtils.loadFromDirectory(SFX_PATH);
        soundExecutionMethod.put(AbstractSound.SHOOT_SOUND, this::playRandomMeow);
    }

    public void playRandomMeow() {
        sfxSounds.keySet().stream()
                .filter(filename -> filename.startsWith("meow"))
                .skip((int) (sfxSounds.size() * Math.random()))
                .findFirst()
                .ifPresent(name -> sfxSounds.get(name).play());
    }

    public void play(AbstractSound sound) {
        soundExecutionMethod.get(sound).run();
    }
}
