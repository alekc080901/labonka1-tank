package ru.mipt.bit.platformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import ru.mipt.bit.platformer.data.GameSettings;
import ru.mipt.bit.platformer.data.LevelFileLoader;
import ru.mipt.bit.platformer.data.LevelLoader;
import ru.mipt.bit.platformer.data.LevelRandomLoader;
import ru.mipt.bit.platformer.game.controls.commands.CommandFactory;
import ru.mipt.bit.platformer.game.controls.commands.CommandType;
import ru.mipt.bit.platformer.game.controls.input.InputInstruction;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.core.entity.*;
import ru.mipt.bit.platformer.game.core.pubsub.Subscriber;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GraphicEntityFactory;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.gdx.utils.GdxTimeCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class GameConfiguration {

    @Bean
    public LevelLoader levelLoader(@Value("${game.level.generation}") String generationInfo,
                                   @Autowired Subscriber baseLevelSubscriber,
                                   @Autowired GameEntityFactory entityFactory) {
        if (generationInfo.equalsIgnoreCase("random")) {
            return new LevelRandomLoader(GdxLevel.getLevelSizeFromFile("level.tmx"),
                    Set.of(baseLevelSubscriber), entityFactory);
        } else if (generationInfo.toLowerCase().endsWith(".level")) {
            return new LevelFileLoader(generationInfo, Set.of(baseLevelSubscriber), entityFactory);
        } else {
            throw new IllegalArgumentException("Incorrect game.level.generation value!");
        }
    }

    // Можно написать красивее со Spring Boot и ConditionalOnProperty
    @Bean
    public BaseLevel baseLevel(@Autowired LevelLoader levelLoader) {
        return levelLoader.load();
    }

    @Bean
    public CommandFactory initCommandFactory(@Value("${game.command.delay.health}") long moveDelay,
                                             @Value("${game.command.delay.move}") long healthDelay,
                                             @Autowired GameSettings settings,
                                             @Autowired Map<InputInstruction, CommandType> accordingTypeMap) {
        return new CommandFactory(moveDelay, healthDelay, settings, accordingTypeMap);
    }

    @Bean
    @Profile("gdx")
    public GdxLevel chooseGdxLevel(@Autowired GraphicEntityFactory entityFactory) {
        return new GdxLevel("level.tmx", entityFactory);
    }

    @Bean
    @Profile("gdx")
    public TimeCounter chooseTimeCounter() {
        return new GdxTimeCounter();
    }

    @Bean
    public Map<InputInstruction, CommandType> accordingTypeMap() {
        Map<InputInstruction, CommandType> accordingType = new HashMap<>();
        accordingType.put(InputInstruction.UP, CommandType.MOVE);
        accordingType.put(InputInstruction.DOWN, CommandType.MOVE);
        accordingType.put(InputInstruction.LEFT, CommandType.MOVE);
        accordingType.put(InputInstruction.RIGHT, CommandType.MOVE);
        accordingType.put(InputInstruction.HEALTH_BAR, CommandType.TOGGLE_HEALTH);
        accordingType.put(InputInstruction.SHOOT, CommandType.SHOOT);
        return accordingType;
    }

    @Bean
    public Map<Class<? extends GameEntity>, String> imagesPath(
            @Value("${game.entity.obstacle.texture}")String greenTreeImagePath,
            @Value("${game.entity.bullet.texture}") String bulletImagePath,
            @Value("${game.entity.tank.texture}") String tankImagePath) {
        Map<Class<? extends GameEntity>, String> imagesPathMap = new HashMap<>();
        imagesPathMap.put(Tank.class, tankImagePath);
        imagesPathMap.put(Obstacle.class, greenTreeImagePath);
        imagesPathMap.put(Bullet.class, bulletImagePath);
        return imagesPathMap;
    }
}
