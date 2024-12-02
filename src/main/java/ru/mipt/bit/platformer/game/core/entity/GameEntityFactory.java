package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerType;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameEntityFactory {

    private final EntityConfig config;
    private static Map<Class<? extends GameEntity>, String> imagesPath = new HashMap<>();

    @Autowired
    public GameEntityFactory(EntityConfig config) {
        this.config = config;
        fillImagesPath();
    }

    public Tank getTank(Coordinates coordinates, PlayerType playerType) {
        return new Tank(coordinates, playerType, config.getTankMovePattern(), config.getTankDefaultSpeed(),
                config.getBulletDefaultSpeed(), config.getBulletDefaultDamage(), config.getTankDefaultHealth(),
                config.getTankDefaultRecharge(), config.getBulletZIndex());
    }

    public Bullet getBullet(ShootableEntity shooter, float speed, float damage) {
        return new Bullet(shooter, speed, config.getBulletMovePattern(), config.getExplosionImagePath(),
                damage, config.getBulletZIndex());
    }

    public Obstacle getObstacle(Coordinates coordinates) {
        return new Obstacle(coordinates, config.getObstacleZIndex());
    }

    public String getGraphicPath(GameEntity entity) {
        return imagesPath.get(entity.getClass());
    }

    private void fillImagesPath() {
        imagesPath.put(Tank.class, config.getTankImagePath());
        imagesPath.put(Obstacle.class, config.getGreenTreeImagePath());
        imagesPath.put(Bullet.class, config.getBulletImagePath());
    }
}
