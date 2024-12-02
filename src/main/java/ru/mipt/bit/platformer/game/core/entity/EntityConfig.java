package ru.mipt.bit.platformer.game.core.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@Component
public class EntityConfig {
    @Value("${game.entity.tank.texture}")
    private String tankImagePath;
    @Value("${game.entity.tank.default-speed}")
    private float tankDefaultSpeed;
    @Value("${game.entity.tank.default-health}")
    private float tankDefaultHealth;
    @Value("${game.entity.tank.recharge}")
    private long tankDefaultRecharge;
    @Value("${game.entity.obstacle.texture}")
    private String greenTreeImagePath;
    @Value("${game.entity.bullet.texture}")
    private String bulletImagePath;
    @Value("${game.entity.explosion.texture}")
    private String explosionImagePath;
    @Value("${game.entity.obstacle.texture}")
    private String obstacleImagePath;
    @Value("${game.entity.bullet.default-speed}")
    private float bulletDefaultSpeed;
    @Value("${game.entity.bullet.default-damage}")
    private float bulletDefaultDamage;
    @Value("${game.entity.tank.z-index}")
    private int tankZIndex;
    @Value("${game.entity.bullet.z-index}")
    private int bulletZIndex;
    @Value("${game.entity.explosion.z-index}")
    private int explosionZIndex;
    @Value("${game.entity.explosion.z-index}")
    private int obstacleZIndex;

    private final EntityMovePattern tankMovePattern = EntityMovePattern.EASE;
    private final EntityMovePattern bulletMovePattern = EntityMovePattern.SIMPLE;

    public String getTankImagePath() {
        return tankImagePath;
    }

    public String getGreenTreeImagePath() {
        return greenTreeImagePath;
    }

    public String getBulletImagePath() {
        return bulletImagePath;
    }

    public String getExplosionImagePath() {
        return explosionImagePath;
    }

    public float getTankDefaultSpeed() {
        return tankDefaultSpeed;
    }

    public float getBulletDefaultSpeed() {
        return bulletDefaultSpeed;
    }

    public float getBulletDefaultDamage() {
        return bulletDefaultDamage;
    }

    public float getTankDefaultHealth() {
        return tankDefaultHealth;
    }

    public EntityMovePattern getTankMovePattern() {
        return tankMovePattern;
    }

    public EntityMovePattern getBulletMovePattern() {
        return bulletMovePattern;
    }

    public String getObstacleImagePath() {
        return obstacleImagePath;
    }

    public int getTankZIndex() {
        return tankZIndex;
    }

    public int getBulletZIndex() {
        return bulletZIndex;
    }

    public int getExplosionZIndex() {
        return explosionZIndex;
    }

    public int getObstacleZIndex() {
        return obstacleZIndex;
    }

    public long getTankDefaultRecharge() {
        return tankDefaultRecharge;
    }
}
