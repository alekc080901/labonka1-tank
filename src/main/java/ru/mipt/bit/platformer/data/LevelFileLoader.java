package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.exceptions.IncorrectFileFormatException;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.EntityConfig;
import ru.mipt.bit.platformer.game.core.entity.Obstacle;
import ru.mipt.bit.platformer.game.core.entity.AbstractSound;
import ru.mipt.bit.platformer.game.core.entity.Tank;
import ru.mipt.bit.platformer.game.core.entity.pubsub.EntitySubscriber;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

public class LevelFileLoader implements LevelLoader {

    private final String filePath;
    private final Set<EntitySubscriber> subscribers;

    public LevelFileLoader(String filePath, Set<EntitySubscriber> subscribers) {
        this.filePath = filePath;
        this.subscribers = subscribers;
    }

    public LevelFileLoader(String filePath) {
        this.filePath = filePath;
        this.subscribers = null;
    }

    @Override
    public BaseLevel load() {
        try (
                InputStream stream = getStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream)
                )) {
            int width = getWidth();
            int height = getHeight();
            BaseLevel level = new BaseLevel(new Coordinates(width, height));
            fillTanksAndObstacles(level, br, height);
            return level;
        } catch (FileNotFoundException e) {
            throw new FileSystemNotFoundException("Level file not found!");
        } catch (IOException e) {
            throw new IncorrectFileFormatException("Error while parsing level:" + e.getMessage());
        }
    }

    private InputStream getStream(String filePath) {
        return Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath));
    }

    private void fillTanksAndObstacles(BaseLevel level, BufferedReader br, int height) throws IOException {
        String line;
        int currentLineNumber = 0;
        while ((line = br.readLine()) != null) {
            currentLineNumber++;
            processLine(level, line, currentLineNumber, height);
        }
    }

    private void processLine(BaseLevel level, String line, int rowNumber, int maxRow) {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            switch (c) {
                case 'T':
                    level.registerEntity(new Obstacle(new Coordinates(i, maxRow - rowNumber)), EntityConfig.GREEN_TREE_IMAGE_PATH, AbstractSound.EMPTY);
                    break;
                case 'X':
                    level.registerEntity(new Tank(new Coordinates(i, maxRow - rowNumber), PlayerTypes.PLAYER), EntityConfig.BLUE_TANK_IMAGE_PATH, AbstractSound.EMPTY);
                    break;
                case 'B':
                    level.registerEntity(new Tank(new Coordinates(i, maxRow - rowNumber), PlayerTypes.SIMPLE_AI), EntityConfig.BLUE_TANK_IMAGE_PATH, AbstractSound.EMPTY);
                    break;
                case '_':
                    break;
                default:
                    throw new FileSystemNotFoundException("Not supported character in level file!");
            }
        }
    }

    private int getWidth() throws IOException {
        String firstLine = Files.lines(Paths.get(filePath), Charset.defaultCharset()).findFirst().orElse("");
        String[] parts = firstLine.split("\\s+");
        if (parts.length > 0) {
            return Integer.parseInt(parts[0]);
        } else {
            throw new IncorrectFileFormatException("Invalid file format!");
        }
    }

    public int getHeight() throws IOException {
        return (int) Files.lines(Paths.get(filePath), Charset.defaultCharset()).count();
    }
}
