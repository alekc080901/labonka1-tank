package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.game.graphics.contracts.TimeCounter;

public class GdxTimeCounter implements TimeCounter {
    @Override
    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
}
