package ru.mipt.bit.platformer.game.gdx.sound;

import com.badlogic.gdx.audio.Sound;
import ru.mipt.bit.platformer.game.gdx.utils.SoundUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SFXPlayer {

    private static final String SFX_PATH = "sound/sfx";
    private final Map<String, Sound> sfxSounds;
    public SFXPlayer() {
        this.sfxSounds = SoundUtils.loadFromDirectory(SFX_PATH);
    }

    public void playRandomMeow() {
        sfxSounds.keySet().stream()
                .filter(filename -> filename.startsWith("meow"))
                .skip((int) (sfxSounds.size() * Math.random()))
                .findFirst()
                .ifPresent(name -> sfxSounds.get(name).play());
    }
}
