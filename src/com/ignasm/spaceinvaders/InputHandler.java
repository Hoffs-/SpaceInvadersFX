package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.commands.*;
import com.ignasm.spaceinvaders.helpers.MovementDirection;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class InputHandler {
    private static final long PLAYER_SHOT_INTERVAL = 1_000_000_000;
    private long playerLastShot = 0;

    private boolean isShooting = false;
    private boolean isSaving = false;
    private boolean isRestoring = false;

    private LocalDateTime pressedLeft = LocalDateTime.now();
    private boolean isMovingLeft = false;

    private LocalDateTime pressedRight = LocalDateTime.now();
    private boolean isMovingRight = false;

    private GameScene scene;

    public InputHandler(GameScene gameScene) {
        this.scene = gameScene;
        setHandlers(gameScene.getWindow());
    }

    private void setHandlers(Node context) {
        context.setOnKeyPressed(this::keyDownHandler);
        context.setOnKeyReleased(this::keyUpHandler);
    }

    private void keyDownHandler(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                isMovingLeft = true;
                pressedLeft = LocalDateTime.now();
                break;
            case D:
                isMovingRight = true;
                pressedRight = LocalDateTime.now();
                break;
            case S:
                if (!event.isControlDown()) break;
                isSaving = true;
                break;
            case R:
                if (!event.isControlDown() && !isSaving) break;
                isRestoring = true;
                break;
            case SPACE:
                isShooting = true;
                break;
        }
    }

    private void keyUpHandler(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                isMovingLeft = false;
                break;
            case D:
                isMovingRight = false;
                break;
            case S:
                isSaving = false;
                break;
            case R:
                isRestoring = false;
                break;
            case SPACE:
                isShooting = false;
                break;
        }
    }

    private MovementDirection getMovementDirection() {
        if (isMovingRight && isMovingLeft) {
            return (pressedLeft.compareTo(pressedRight) > 0) ? MovementDirection.LEFT : MovementDirection.RIGHT;
        } else if (isMovingLeft) {
            return MovementDirection.LEFT;
        } else if (isMovingRight) {
            return MovementDirection.RIGHT;
        } else {
            return MovementDirection.STILL;
        }
    }

    public Command[] getCommands(long now) {
        List<Command> commands = new ArrayList<>();
        if (canPlayerShoot(now) && isShooting) {
            playerLastShot = now;
            commands.add(new PlayerShootCommand(scene.getPlayer(), scene.getPlayerShots()));
        }
        if (getMovementDirection() != MovementDirection.STILL) {
            commands.add(new MovementCommand(scene.getPlayer(), getMovementDirection(), scene.getGameSpeed()));
        }
        if (isSaving) {
            commands.add(new SaveCommand(scene));
            isSaving = false;
        }
        if (isRestoring) {
            commands.add(new RestoreCommand(scene));
            isRestoring = false;
        }
        return commands.toArray(new Command[commands.size()]);
    }

    private boolean canPlayerShoot(long now) {
        return now - playerLastShot > PLAYER_SHOT_INTERVAL;
    }
}
