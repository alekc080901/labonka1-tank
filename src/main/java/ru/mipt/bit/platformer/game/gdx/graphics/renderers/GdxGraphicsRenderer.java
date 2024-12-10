package ru.mipt.bit.platformer.game.gdx.graphics.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.renderers.GraphicsRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils.createSingleLayerMapRenderer;

@Component
//@Profile("gdx")
public class GdxGraphicsRenderer implements GraphicsRenderer {
    /*
    Класс, отвечающий за отрисовку и обновление уровня (карты).
     */
    private final MapRenderer mapRenderer;
    private final GdxLevel level;
    private final Batch batch;

    @Autowired
    public GdxGraphicsRenderer(GdxLevel level) {
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
    public void render(float deltaTime) {
        mapRenderer.render();
        batch.begin();
        level.drawEntities(batch, deltaTime);
        batch.end();
    }

    @Override
    public void dispose() {
        level.dispose();
        batch.dispose();
    }
}