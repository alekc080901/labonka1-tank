package ru.mipt.bit.platformer.game.gdx.utils;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.core.TimeCounter;

public class GdxTimeCounter implements TimeCounter {
    @Override
    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
}
