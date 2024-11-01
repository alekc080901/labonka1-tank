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
import ru.mipt.bit.platformer.game.graphics.contracts.LevelRenderer;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GdxLevelRenderer implements LevelRenderer {
    /*
    Класс, отвечающий за отрисовку и обновление уровня (карты).
     */
    private final MapRenderer mapRenderer;
    private final GdxLevel level;
    private final Batch batch;

    public GdxLevelRenderer(GdxLevel level) {
        this.level = level;
        this.batch = new SpriteBatch();
        this.mapRenderer = createSingleLayerMapRenderer(level.getMapObject(), batch);
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

    public BaseLevel getLevel() {
        return level.getSourceLevel();
    }

    @Override
    public void dispose() {
        level.dispose();
        batch.dispose();
    }

    @Override
    public BaseLevel getSource() {
        return level.getSourceLevel();
    }
}