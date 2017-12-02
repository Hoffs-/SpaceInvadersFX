package com.ignasm.spaceinvaders.helpers;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public enum MovementDirection {
    LEFT(-1),
    RIGHT(1),
    STILL(0),
    UP(-1),
    DOWN(1);

    private int value;

    MovementDirection(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public static MovementDirection getByValue(int value) {
        for (MovementDirection movementDirection : MovementDirection.values()) {
            if (value == movementDirection.value) return movementDirection;
        }
        return null;
    }
}
