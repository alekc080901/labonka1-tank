package ru.mipt.bit.platformer.data;

import org.jetbrains.annotations.NotNull;
import ru.mipt.bit.platformer.exceptions.IncorrectFileFormatException;
import ru.mipt.bit.platformer.exceptions.NotFoundException;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.Obstacle;
import ru.mipt.bit.platformer.game.core.Tank;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LevelFileLoader implements MapLoader {

    private final Set<Tank> tanks = new HashSet<>();
    private final Set<Obstacle> obstacles = new HashSet<>();

    @Override
    public BaseLevel load(String filePath) {
        int width = -1;
        int height;
        try (
                InputStream stream = getStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream)
        )) {
            height = countLines(filePath);

            String line;
            int currentLineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (width != line.length() && width != -1) {
                    throw new NotFoundException("Error while parsing level file!");
                }
                width = line.length();
                currentLineNumber++;

                processLine(line, currentLineNumber, height);
            }
        } catch (FileNotFoundException e) {
            throw new NotFoundException("Level file not found!");
        } catch (IOException e) {
            throw new IncorrectFileFormatException("Error while parsing level:" + e.getMessage());
        }

        return new BaseLevel(tanks, obstacles, new Coordinates(width, height));
    }

    @NotNull
    private InputStream getStream(String filePath) {
        return Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath));
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

    public int countLines(String filename) throws IOException {
        try (InputStream is = getStream(filename)) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
            return count;
        }
    }
}
