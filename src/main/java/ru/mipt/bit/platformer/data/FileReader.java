package ru.mipt.bit.platformer.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileReader {
    public static Collection<String> listFilesForFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) throw new IOException();
        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(File::isFile)
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    public static URL getResource(String dirPath) throws IOException {
        return Thread.currentThread().getContextClassLoader().getResource(dirPath);
    }
}
