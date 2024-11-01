package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.graphics.contracts.Entity;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GdxEntity implements Entity {
    /*
    Класс объекта, размещенного на карте. Может быть кем угодно, лишь бы была нужная текстурка.
    */
    private final GameEntity gameEntity;
    private final Texture texture;
    private float rotation = 0f;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public GdxEntity(GameEntity entity, String texturePath) {
        this.gameEntity = entity;
        this.texture = new Texture(texturePath);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Coordinates getCoordinates() {
        return gameEntity.getCoordinates();
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }
}
