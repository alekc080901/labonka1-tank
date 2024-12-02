package ru.mipt.bit.platformer.game.gdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import ru.mipt.bit.platformer.data.FileReader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class SoundUtils {

    public static Map<String, Sound> loadFromDirectory(String dirPath) {
        Map<String, Sound> sounds = new HashMap<>();
        try {
            URL dirURL = FileReader.getResource(dirPath);
            if (dirURL == null) throw new IOException();

            Collection<String> bgmPaths = FileReader.listFilesForFolder(dirURL.getPath());
            for (String bgmPath : bgmPaths) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(bgmPath));
                sounds.put(Paths.get(bgmPath).getFileName().toString(), sound);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read from BGM path!");
        }
        return sounds;
    }
}
