package ru.mipt.bit.platformer.game.gdx.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.KillableEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.texture.TextureDrawer;
import ru.mipt.bit.platformer.game.gdx.utils.GdxGameUtils;

class HealthDecorator implements GraphicEntityDecorator {
    private static final int TOTAL_SEGMENTS = 8;

    private final GraphicEntity wrappedEntity;

    public HealthDecorator(GraphicEntity entity) {
        this.wrappedEntity = entity;
    }

    @Override
    public void dispose() {
        wrappedEntity.dispose();
    }

    @Override
    public void draw(Batch batch, float deltaTime) {
        wrappedEntity.draw(batch, deltaTime);
        drawHealthBar(batch);
    }

    @Override
    public GameEntity getGameEntity() {
        return wrappedEntity.getGameEntity();
    }

    @Override
    public TextureDrawer getTexture() {
        return wrappedEntity.getTexture();
    }

    private void drawHealthBar(Batch batch) {
        if (wrappedEntity.getGameEntity() instanceof KillableEntity) {
            KillableEntity gameEntity = (KillableEntity) wrappedEntity.getGameEntity();
            float health = gameEntity.getCurrentHealth();
            float maxHealth = gameEntity.getMaxHealth();

            TextureRegion healthBarTexture = getHealthBarTexture(health, maxHealth);
            Rectangle rectangle = createRectangle();
            GdxGameUtils.drawTextureRegionUnscaled(batch, healthBarTexture, rectangle, 0f);
        }
    }

    private TextureRegion getHealthBarTexture(float currentHealth, float maxHealth) {
        var pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        float healthRatio = currentHealth / maxHealth;

        int healthBarWidth = Math.round(wrappedEntity.getTexture().getRectangle().width);
        int segmentWidth = healthBarWidth / TOTAL_SEGMENTS;
        for (int i = 0; i < TOTAL_SEGMENTS; i++) {
            if ((float) i / TOTAL_SEGMENTS < healthRatio) {
                pixmap.setColor(Color.GREEN);
            } else {
                pixmap.setColor(Color.valueOf("#A2A2A2"));
            }
            pixmap.fillRectangle(i * segmentWidth, 0, segmentWidth - 2, 20);
        }
        var texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    private Rectangle createRectangle() {
        var rectangle = new Rectangle(wrappedEntity.getTexture().getRectangle());
        rectangle.y += 90;
        return rectangle;
    }
}
