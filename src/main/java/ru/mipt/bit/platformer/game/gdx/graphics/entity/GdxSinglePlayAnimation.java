package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;

public class GdxSinglePlayAnimation implements GraphicEntity {

    private final GdxEntity gdxEntity;

    public GdxSinglePlayAnimation(GdxEntity gdxEntity) {
        this.gdxEntity = gdxEntity;
        if (!(gdxEntity.getTexture() instanceof GifTexture)) {
            throw new IllegalStateException("Try to show animation with not gif file!");
        }
    }

    public boolean finishedPlaying() {
        return ((GifTexture) gdxEntity.getTexture()).isFinished();
    }

    @Override
    public void dispose() {
        gdxEntity.dispose();
    }

    @Override
    public void draw(Batch batch, float deltaTime) {
        gdxEntity.draw(batch, deltaTime);
    }

    @Override
    public GameEntity getGameEntity() {
        return gdxEntity.getGameEntity();
    }

    @Override
    public TextureDrawer getTexture() {
        return gdxEntity.getTexture();
    }

    @Override
    public void setRotation(float rotation) {
        gdxEntity.setRotation(rotation);
    }
}
