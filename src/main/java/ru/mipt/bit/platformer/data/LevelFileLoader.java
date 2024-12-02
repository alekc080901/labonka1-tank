package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.exceptions.IncorrectFileFormatException;
import ru.mipt.bit.platformer.game.controls.input.generators.InstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.PlayerInstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.SimpleAIInstructionGenerator;
import ru.mipt.bit.platformer.game.core.*;
import ru.mipt.bit.platformer.game.core.entity.*;
import ru.mipt.bit.platformer.game.core.pubsub.Subscriber;
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
    private final Set<Subscriber> subscribers;
    private final GameEntityFactory entityFactory;
    private final EntityConfig entityConfig;

    public LevelFileLoader(String filePath, Set<Subscriber> subscribers, EntityConfig entityConfig,
                           GameEntityFactory entityFactory) {
        this.filePath = filePath;
        this.subscribers = subscribers;
        this.entityFactory = entityFactory;
        this.entityConfig = entityConfig;
    }

    @Override
    public BaseLevel load() {
        try (
                InputStream stream = getStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream)
                )) {
            int width = getWidth();
            int height = getHeight();
            BaseLevel level = new BaseLevel(new Coordinates(width, height), entityFactory);
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
                    var obstacle = entityFactory.getObstacle(new Coordinates(i, maxRow - rowNumber));
                    level.registerEntity(obstacle, entityFactory.getGraphicPath(obstacle), AbstractSound.EMPTY);
                    break;
                case 'X':
                    entityFactory.registerTank(level,  PlayerType.PLAYER, new Coordinates(i, maxRow - rowNumber));
                    break;
                case 'B':
                    entityFactory.registerTank(level,  PlayerType.SIMPLE_AI, new Coordinates(i, maxRow - rowNumber));
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
