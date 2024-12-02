package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public interface TextureDrawer {
    void draw(Batch batch, float deltaTime, float rotation);
    void dispose();
    Rectangle getRectangle();
}
