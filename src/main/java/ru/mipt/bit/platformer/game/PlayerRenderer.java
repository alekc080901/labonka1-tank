package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.game.controls.commands.Command;
import ru.mipt.bit.platformer.game.controls.commands.MoveCommand;
import ru.mipt.bit.platformer.game.core.Player;
import ru.mipt.bit.platformer.game.core.PlayerMoveLogic;
import ru.mipt.bit.platformer.game.graphics.Renderer;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerRenderer {
    /*
    Класс, ответственный за отображение перемещения игрока на уровне. Обертка над логикой PlayerMoveLogic для рендера
    текстуры игрока на поле.
     */

    private final PlayerMoveLogic playerMoveLogic;
    private final Renderer renderer;
    private final Player player;
    public static final float MOVEMENT_SPEED = 0.4f;
    private float playerMovementProgress = 1f;

    public PlayerRenderer(PlayerMoveLogic playerMoveLogic, Renderer renderer) {
        this.player = playerMoveLogic.getPlayer();
        this.playerMoveLogic = playerMoveLogic;
        this.renderer = renderer;
    }

    public void handleCommand(Command command, float deltaTime) {
        if (command instanceof MoveCommand) {
            movePlayer((MoveCommand) command);
        }

        // This function does not require Gdx engine to work
        playerMovementProgress = continueProgress(playerMovementProgress, deltaTime, MOVEMENT_SPEED);

        renderer.shiftEntity(
                renderer.getRenderedEntity(player), playerMoveLogic.getDestination(), playerMovementProgress
        );
    }

    private void movePlayer(MoveCommand command) {
        if (isEqual(playerMovementProgress, 1f)) {
            // Finish previous move (do nothing if move hasn't been started)
            playerMoveLogic.finishMove();

            boolean hasStarted = playerMoveLogic.startMove(command);
            // Rotation for playerMoveLogic and player on screen are different entities
            renderer.getRenderedEntity(player).setRotation(playerMoveLogic.getRotation());
            if (hasStarted) {
                playerMovementProgress = 0f;
            }
        }
    }
}
