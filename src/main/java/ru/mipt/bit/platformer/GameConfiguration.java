package ru.mipt.bit.platformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import ru.mipt.bit.platformer.data.GameSettings;
import ru.mipt.bit.platformer.data.LevelFileLoader;
import ru.mipt.bit.platformer.data.LevelLoader;
import ru.mipt.bit.platformer.data.LevelRandomLoader;
import ru.mipt.bit.platformer.game.controls.commands.CommandFactory;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.core.entity.EntityConfig;
import ru.mipt.bit.platformer.game.core.entity.GameEntityFactory;
import ru.mipt.bit.platformer.game.core.pubsub.Subscriber;
import ru.mipt.bit.platformer.game.core.level.BaseLevel;
import ru.mipt.bit.platformer.game.gdx.graphics.entity.GraphicEntityFactory;
import ru.mipt.bit.platformer.game.gdx.graphics.level.GdxLevel;
import ru.mipt.bit.platformer.game.gdx.utils.GdxTimeCounter;

import java.util.Set;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class GameConfiguration {

    // Можно написать красивее со Spring Boot и ConditionalOnProperty
    @Bean
    public BaseLevel chooseBaseLevel(@Value("${game.level.generation}") String generationInfo,
                                     @Autowired Subscriber baseLevelSubscriber,
                                     @Autowired EntityConfig entityConfig,
                                     @Autowired GameEntityFactory entityFactory) {
        LevelLoader levelLoader;
        if (generationInfo.equalsIgnoreCase("random")) {
            levelLoader = new LevelRandomLoader(GdxLevel.getLevelSizeFromFile("level.tmx"),
                    Set.of(baseLevelSubscriber), entityConfig, entityFactory);
        } else if (generationInfo.toLowerCase().endsWith(".level")) {
            levelLoader = new LevelFileLoader(generationInfo, Set.of(baseLevelSubscriber), entityConfig, entityFactory);
        } else {
            throw new IllegalArgumentException("Incorrect game.level.generation value!");
        }
        return levelLoader.load();
    }

    @Bean
    public CommandFactory initCommandFactory(@Value("${game.command.delay.health}") long moveDelay,
                                             @Value("${game.command.delay.move}") long healthDelay,
                                             @Autowired GameSettings settings) {
        return new CommandFactory(moveDelay, healthDelay, settings);
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
}
