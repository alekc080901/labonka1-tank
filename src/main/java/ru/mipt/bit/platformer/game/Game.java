package ru.mipt.bit.platformer.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandPublisher;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandReceiver;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.gdx.sound.BGMPlayer;
import ru.mipt.bit.platformer.game.renderers.GameRenderer;

@Component
public class Game {

    private final GameRenderer gameRenderer;
    private final TimeCounter timeCounter;
    private final CommandPublisher commandPublisher;
    private final CommandReceiver commandReceiver;
    private final BGMPlayer bgmPlayer;

    @Autowired
    public Game(GameRenderer gameRenderer,
                TimeCounter timeCounter,
                CommandPublisher commandPublisher,
                CommandReceiver commandReceiver,
                BGMPlayer bgmPlayer) {
        this.gameRenderer = gameRenderer;
        this.timeCounter = timeCounter;
        this.commandPublisher = commandPublisher;
        this.commandReceiver = commandReceiver;
        this.bgmPlayer = bgmPlayer;

        onInit();
    }

    public void onInit() {
        bgmPlayer.play();
    }

    public void tick() {
        float deltaTime = timeCounter.getDelta();
        commandPublisher.publishAll();
        commandReceiver.processAll();
        gameRenderer.render(deltaTime);
    }

    public void stop() {
        gameRenderer.stop();
    }

}
