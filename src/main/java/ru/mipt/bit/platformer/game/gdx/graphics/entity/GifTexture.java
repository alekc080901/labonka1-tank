package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.gdx.utils.GifDecoder;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class GifTexture implements TextureDrawer {
    private final Texture texture;
    private final Animation<TextureRegion> graphics;
    private final Rectangle rectangle;
    private float elapsed;

    public GifTexture(String path) {
        this.texture = new Texture(path);
        this.graphics = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(path).read());
        this.rectangle = createBoundingRectangle(graphics.getKeyFrame(0));
        graphics.setFrameDuration(0.03f);
    }

    @Override
    public void draw(Batch batch, float deltaTime, float rotation) {
        elapsed += deltaTime;
        drawTextureRegionUnscaled(batch, graphics.getKeyFrame(elapsed), rectangle, rotation);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isFinished() {
        return graphics.isAnimationFinished(elapsed);
    }
}
