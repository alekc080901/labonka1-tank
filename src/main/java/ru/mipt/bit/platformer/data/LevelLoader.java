package ru.mipt.bit.platformer.data;

import ru.mipt.bit.platformer.game.core.level.BaseLevel;

public interface LevelLoader {
    BaseLevel load();
}
