package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.helpers.MovementDirection;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;

import java.time.LocalDateTime;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class InputHandler {
    private static InputHandler instance = new InputHandler();
    private boolean isShooting = false;

    private LocalDateTime pressedLeft = LocalDateTime.now();
    private boolean isMovingLeft = false;

    private LocalDateTime pressedRight = LocalDateTime.now();
    private boolean isMovingRight = false;

    private InputHandler(){};

    public static InputHandler getInstance() {
        return instance;
    }

    public static void setHandlers(Node context) {
        context.setOnKeyPressed(getInstance()::keyDownHandler);
        context.setOnKeyReleased(getInstance()::keyUpHandler);
    }

    public void keyDownHandler(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                isMovingLeft = true;
                pressedLeft = LocalDateTime.now();
                break;
            case D:
                isMovingRight = true;
                pressedRight = LocalDateTime.now();
                break;
            case SPACE:
                isShooting = true;
                break;
        }
    }

    public void keyUpHandler(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                isMovingLeft = false;
                break;
            case D:
                isMovingRight = false;
                break;
            case SPACE:
                isShooting = false;
                break;
        }
    }

    public boolean getIsShooting() {
        return isShooting;
    }

    public MovementDirection getMovementDirection() {
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
}
