package ru.mipt.bit.platformer.game.gdx.graphics.renderers;

import com.badlogic.gdx.math.GridPoint2;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.entity.GameEntity;
import ru.mipt.bit.platformer.game.core.entity.MovableEntity;
import ru.mipt.bit.platformer.game.core.entity.RotatableEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GraphicEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GdxMoveEntity;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.renderers.GraphicsMoveRenderer;
import ru.mipt.bit.platformer.game.gdx.utils.TileMovement;

@Component
public class GdxMoveRenderer implements GraphicsMoveRenderer {
    private final GdxLevel level;
    private final TileMovement tileMovement;

    public GdxMoveRenderer(GdxLevel level) {
        this.level = level;
        this.tileMovement = new TileMovement(level.getGroundLayer());
    }

    @Override
    public void shiftEntity(MovableEntity entity) {
        Coordinates dest = entity.getDestination();
        var gdxMoveEntity = (GdxMoveEntity) level.getGraphicFromGame(entity);
        if (gdxMoveEntity == null) return;

        var coords = gdxMoveEntity.getGameEntity().getCoordinates();
        tileMovement.moveRectangleBetweenTileCenters(
                gdxMoveEntity.getTexture().getRectangle(), new GridPoint2(coords.x, coords.y), new GridPoint2(dest.x, dest.y), gdxMoveEntity.getInterpolationMethod(), entity.getProgress()
        );
    }

    @Override
    public void turnEntity(RotatableEntity entity) {
        GraphicEntity gdxEntity = level.getGraphicFromGame(entity);
        if (gdxEntity == null) return;
        entity.turn(entity.getRotation());
    }
}
