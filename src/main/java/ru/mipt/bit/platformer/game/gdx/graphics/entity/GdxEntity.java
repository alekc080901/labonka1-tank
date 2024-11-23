package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.jetbrains.annotations.NotNull;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.gdx.utils.GifDecoder;

import java.util.Set;

import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.drawTextureRegionUnscaled;

public class GdxEntity implements Entity {
    /*
    Класс объекта, размещенного на карте. Может быть кем угодно, лишь бы была нужная текстурка.
    */
    private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg", "jpeg");
    private static final Set<String> GIF_EXTENSIONS = Set.of("gif");
    private final GameEntity gameEntity;
    private float rotation = 0f;
    private final TextureDrawer textureDrawer;

    public GdxEntity(GameEntity entity, String texturePath) {
        this.gameEntity = entity;
        this.textureDrawer = buildTexture(texturePath);
    }

    @NotNull
    private static TextureDrawer buildTexture(String texturePath) {
        String fileExtension = texturePath.substring(texturePath.lastIndexOf('.') + 1).toLowerCase();
        if (IMAGE_EXTENSIONS.contains(fileExtension)) {
            return new ImageTexture(texturePath);
        } else if (GIF_EXTENSIONS.contains(fileExtension)) {
            return new GifTexture(texturePath);
        }
        throw new IllegalStateException("Wrong texture file extension!");
    }

    @Override
    public void draw(Batch batch, float deltaTime) {
        textureDrawer.draw(batch, deltaTime, rotation);
    }

    @Override
    public void dispose() {
        textureDrawer.dispose();
    }

    public TextureDrawer getTexture() {
        return textureDrawer;
    }

    public Coordinates getCoordinates() {
        return gameEntity.getCoordinates();
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public GameEntity getGameEntity() {
        return gameEntity;
    }
}
