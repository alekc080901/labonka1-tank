package ru.mipt.bit.platformer.game.core;

import org.springframework.stereotype.Component;

@Component
public interface TimeCounter {
    float getDelta();
}
