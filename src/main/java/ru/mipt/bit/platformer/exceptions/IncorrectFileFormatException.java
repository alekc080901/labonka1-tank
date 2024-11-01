package ru.mipt.bit.platformer.exceptions;

public class IncorrectFileFormatException extends RuntimeException {
    public IncorrectFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
