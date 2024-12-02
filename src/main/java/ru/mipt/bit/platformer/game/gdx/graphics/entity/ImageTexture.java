package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class ImageTexture implements TextureDrawer {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public ImageTexture(String path) {
        this.texture = new Texture(path);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
    }

    @Override
    public void draw(Batch batch, float deltaTime, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
