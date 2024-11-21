package ru.mipt.bit.platformer.game.gdx.graphics.renderer;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxMoveEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.render.MoveRenderer;
import ru.mipt.bit.platformer.game.gdx.utils.TileMovement;

public class GdxMoveRenderer implements MoveRenderer {
    private final GdxLevel level;
    private final TileMovement tileMovement;

    public GdxMoveRenderer(GdxLevel level) {
        this.level = level;
        this.tileMovement = new TileMovement(level.getGroundLayer());
    }

    @Override
    public void shiftEntity(MovableEntity entity) {
        Coordinates dest = entity.getDestination();
        GdxMoveEntity gdxEntity = getRenderedEntity(entity);
        Coordinates coords = gdxEntity.getCoordinates();
        tileMovement.moveRectangleBetweenTileCenters(
                gdxEntity.getRectangle(), new GridPoint2(coords.x, coords.y), new GridPoint2(dest.x, dest.y), gdxEntity.getInterpolationMethod(), entity.getProgress()
        );
    }

    @Override
    public void turnEntity(GameEntity entity) {
        GdxEntity gdxEntity = getRenderedEntity(entity);
        gdxEntity.setRotation(entity.getRotation());
    }

    private GdxMoveEntity getRenderedEntity(GameEntity gameEntity) {
        return (GdxMoveEntity) level.getGdxObjectFromEntity(gameEntity);
    }
}
