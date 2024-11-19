package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.core.GameEntity;

public interface Entity {
    void dispose();
    void draw(Batch batch);
    GameEntity getGameEntity();
    Rectangle getRectangle();
    TextureRegion getGraphics();
    void setRotation(float rotation);
}
