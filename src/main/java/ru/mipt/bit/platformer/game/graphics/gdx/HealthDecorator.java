package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.KillableEntity;
import ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils;

class HealthDecorator implements EntityDecorator {
    private static final int TOTAL_SEGMENTS = 8;

    private final Entity wrappedEntity;

    public HealthDecorator(Entity entity) {
        this.wrappedEntity = entity;
    }

    @Override
    public void dispose() {
        wrappedEntity.dispose();
    }

    @Override
    public void draw(Batch batch) {
        wrappedEntity.draw(batch);
        drawHealthBar(batch);
    }

    @Override
    public GameEntity getGameEntity() {
        return (KillableEntity) wrappedEntity.getGameEntity();
    }

    @Override
    public Rectangle getRectangle() {
        return wrappedEntity.getRectangle();
    }

    @Override
    public TextureRegion getGraphics() {
        return wrappedEntity.getGraphics();
    }

    @Override
    public void setRotation(float rotation) {
        wrappedEntity.setRotation(rotation);
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

        int healthBarWidth = wrappedEntity.getGraphics().getRegionWidth();
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
        var rectangle = new Rectangle(wrappedEntity.getRectangle());
        rectangle.y += 90;
        return rectangle;
    }
}