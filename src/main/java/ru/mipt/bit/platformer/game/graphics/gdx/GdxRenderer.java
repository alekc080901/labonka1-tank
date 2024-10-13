package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.BaseLevel;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.graphics.Entity;
import ru.mipt.bit.platformer.game.graphics.Renderer;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GdxRenderer implements Renderer {
    /*
    Класс, отвечающий за отрисовку и обновление уровня (карты).
     */
    private final MapRenderer mapRenderer;
    private final GdxLevel level;
    private final Batch batch;
    private final TileMovement tileMovement;

    public GdxRenderer(GdxLevel level) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.mapRenderer = createSingleLayerMapRenderer(level.getMapObject(), batch);
        this.tileMovement = new TileMovement(level.getGroundLayer(), Interpolation.smooth);
    }

    @Override
    public void clear() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render() {
        mapRenderer.render();
        batch.begin();
        level.drawEntities(batch);
        batch.end();
    }

    @Override
    public void shiftEntity(Entity entity, Coordinates dest, float progress) {
        GdxEntity gdxEntity = (GdxEntity) entity;
        Coordinates coords = gdxEntity.getCoordinates();
        tileMovement.moveRectangleBetweenTileCenters(
                gdxEntity.getRectangle(), new GridPoint2(coords.x, coords.y), new GridPoint2(dest.x, dest.y), progress
        );
    }

    public BaseLevel getLevel() {
        return level.getSourceLevel();
    }

    @Override
    public GdxEntity getRenderedEntity(GameEntity gameEntity) {
        return level.getGdxObjectFromEntity(gameEntity);
    }

    public void dispose() {
        level.dispose();
        batch.dispose();
    }
}
