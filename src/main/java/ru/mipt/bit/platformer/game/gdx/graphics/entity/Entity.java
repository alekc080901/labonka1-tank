package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public interface Entity {
    void dispose();
    void draw(Batch batch, float deltaTime);
    GameEntity getGameEntity();
    TextureDrawer getTexture();
    void setRotation(float rotation);
}
