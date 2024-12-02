package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.controls.input.generators.PlayerInstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.SimpleAIInstructionGenerator;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerType;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;

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

    public Tank getTank(Coordinates coordinates) {
        return new Tank(coordinates, config.getTankMovePattern(), config.getTankDefaultSpeed(),
                config.getBulletDefaultSpeed(), config.getBulletDefaultDamage(), config.getTankDefaultHealth(),
                config.getTankDefaultRecharge(), config.getBulletZIndex());
    }

    public Bullet getBullet(ShootableEntity shooter, float speed, float damage) {
        return new Bullet(shooter, speed, config.getBulletMovePattern(), config.getExplosionImagePath(),
                damage, config.getBulletZIndex());
    }

    public void registerTank(BaseLevel level, PlayerType playerType, Coordinates coordinates) {
        var tank = getTank(coordinates);
        level.registerEntity(tank, getGraphicPath(tank), AbstractSound.EMPTY);
        switch (playerType) {
            case PLAYER:
                tank.setDriver(new PlayerInstructionGenerator(tank));
                break;
            case SIMPLE_AI:
                tank.setDriver(new SimpleAIInstructionGenerator(tank));
                break;
        }
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
