package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.controls.input.generators.PlayerInstructionGenerator;
import ru.mipt.bit.platformer.game.controls.input.generators.SimpleAIInstructionGenerator;
import ru.mipt.bit.platformer.game.core.Coordinates;
import ru.mipt.bit.platformer.game.core.PlayerType;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameEntityFactory {

    @Value("${game.entity.tank.default-speed}")
    private float tankDefaultSpeed;
    @Value("${game.entity.tank.default-health}")
    private float tankDefaultHealth;
    @Value("${game.entity.tank.recharge}")
    private long tankDefaultRecharge;
    @Value("${game.entity.bullet.default-speed}")
    private float bulletDefaultSpeed;
    @Value("${game.entity.bullet.default-damage}")
    private float bulletDefaultDamage;
    @Value("${game.entity.explosion.texture}")
    private String explosionImagePath;

    private final EntityMovePattern tankMovePattern = EntityMovePattern.EASE;
    private final EntityMovePattern bulletMovePattern = EntityMovePattern.SIMPLE;

    private final Map<InputInstruction, CommandType> accordingTypesMap;
    private final Map<Class<? extends GameEntity>, String> imagesPath;

    @Autowired
    public GameEntityFactory(Map<InputInstruction, CommandType> accordingTypesMap,
                             Map<Class<? extends GameEntity>, String> imagesPath) {
        this.accordingTypesMap = accordingTypesMap;
        this.imagesPath = imagesPath;
    }

    public Tank getTank(Coordinates coordinates) {
        return new Tank(coordinates, tankMovePattern, tankDefaultSpeed,
                bulletDefaultSpeed, bulletDefaultDamage, tankDefaultHealth,
                tankDefaultRecharge);
    }

    public Bullet getBullet(ShootableEntity shooter, float speed, float damage) {
        return new Bullet(shooter, speed, bulletMovePattern, explosionImagePath, damage);
    }

    public void registerTank(BaseLevel level, PlayerType playerType, Coordinates coordinates) {
        var tank = getTank(coordinates);
        level.registerEntity(tank, getGraphicPath(tank), AbstractSound.EMPTY);
        switch (playerType) {
            case PLAYER:
                tank.setDriver(new PlayerInstructionGenerator(tank, accordingTypesMap));
                break;
            case SIMPLE_AI:
                tank.setDriver(new SimpleAIInstructionGenerator(tank));
                break;
        }
    }

    public Obstacle getObstacle(Coordinates coordinates) {
        return new Obstacle(coordinates);
    }

    public String getGraphicPath(GameEntity entity) {
        return imagesPath.get(entity.getClass());
    }
}
