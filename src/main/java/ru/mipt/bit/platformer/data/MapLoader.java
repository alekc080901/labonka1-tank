package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.game.core.BaseLevel;

public interface MapLoader {
    BaseLevel load(String path);
}
