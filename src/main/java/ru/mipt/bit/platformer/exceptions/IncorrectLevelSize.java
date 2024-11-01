package ru.mipt.bit.platformer.exceptions;

public class IncorrectLevelSize extends RuntimeException {
    public IncorrectLevelSize(String errorMessage) {
        super(errorMessage);
    }
}
