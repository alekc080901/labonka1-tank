package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.exceptions.IncorrectFileFormatException;
import ru.mipt.bit.platformer.exceptions.NotFoundException;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.Obstacle;
import ru.mipt.bit.platformer.game.core.Tank;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MapFileLoader implements MapLoader {

    private final String filePath;
    private final Set<Tank> tanks = new HashSet<>();
    private final Set<Obstacle> obstacles = new HashSet<>();

    public MapFileLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public BaseLevel load() {
        try (
                InputStream stream = getStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream)
                )) {
            int width = getWidth();
            int height = getHeight();
            fillTanksAndObstacles(br, height);
            return new BaseLevel(tanks, obstacles, new Coordinates(width, height));
        } catch (FileNotFoundException e) {
            throw new NotFoundException("Level file not found!");
        } catch (IOException e) {
            throw new IncorrectFileFormatException("Error while parsing level:" + e.getMessage());
        }
    }

    private InputStream getStream(String filePath) {
        return Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath));
    }

    private void fillTanksAndObstacles(BufferedReader br, int height) throws IOException {
        String line;
        int currentLineNumber = 0;
        while ((line = br.readLine()) != null) {
            currentLineNumber++;
            processLine(line, currentLineNumber, height);
        }
    }

    private void processLine(String line, int rowNumber, int maxRow) {
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            switch (c) {
                case 'T':
                    obstacles.add(new Obstacle(new Coordinates(i, maxRow - rowNumber)));
                    break;
                case 'X':
                    tanks.add(new Tank(new Coordinates(i, maxRow - rowNumber)));
                    break;
                case '_':
                    break;
                default:
                    throw new NotFoundException("Not supported character in level file!");
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
