package ru.mipt.bit.platformer.game.graphics.gdx;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.GameEntity;
import ru.mipt.bit.platformer.game.core.MovableEntity;
import ru.mipt.bit.platformer.game.graphics.contracts.MoveRenderer;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;

public class GdxMoveRenderer implements MoveRenderer {
    private final GdxLevel level;
    private final TileMovement tileMovement;

    public GdxMoveRenderer(GdxLevel level) {
        this.level = level;
        this.tileMovement = new TileMovement(level.getGroundLayer(), Interpolation.smooth);
    }

    @Override
    public void shiftEntity(MovableEntity entity) {
        Coordinates dest = entity.getDestination();
        GdxEntity gdxEntity = getRenderedEntity(entity);
        Coordinates coords = gdxEntity.getCoordinates();
        tileMovement.moveRectangleBetweenTileCenters(
                gdxEntity.getRectangle(), new GridPoint2(coords.x, coords.y), new GridPoint2(dest.x, dest.y), entity.getProgress()
        );
    }

    @Override
    public void turnEntity(GameEntity entity) {
        GdxEntity gdxEntity = getRenderedEntity(entity);
        gdxEntity.setRotation(entity.getRotation());
    }

    private GdxEntity getRenderedEntity(GameEntity gameEntity) {
        return level.getGdxObjectFromEntity(gameEntity);
    }
}
