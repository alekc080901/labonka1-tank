package ru.mipt.bit.platformer.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandPublisher;
import ru.mipt.bit.platformer.game.controls.command_queue.CommandReceiver;
import ru.mipt.bit.platformer.game.core.TimeCounter;
import ru.mipt.bit.platformer.game.gdx.sound.BGMPlayer;
import ru.mipt.bit.platformer.game.gdx.sound.SFXPlayer;
import ru.mipt.bit.platformer.game.render.AppRenderer;

@Component
public class Game {

    private final AppRenderer appRenderer;
    private final TimeCounter timeCounter;
    private final CommandPublisher commandPublisher;
    private final CommandReceiver commandReceiver;
    private final BGMPlayer bgmPlayer;

    @Autowired
    public Game(AppRenderer appRenderer,
                TimeCounter timeCounter,
                CommandPublisher commandPublisher,
                CommandReceiver commandReceiver,
                BGMPlayer bgmPlayer) {
        this.appRenderer = appRenderer;
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
        appRenderer.render(deltaTime);
    }

    public void stop() {
        appRenderer.stop();
    }

}
