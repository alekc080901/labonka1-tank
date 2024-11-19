package ru.mipt.bit.platformer.game.gdx.graphics;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.graphic_contracts.TimeCounter;

public class GdxTimeCounter implements TimeCounter {
    @Override
    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
}
