package com.ignasm.spaceinvaders.helpers;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public enum MovementDirection {
    LEFT(-1),
    RIGHT(1),
    STILL(0);

    private int value;

    MovementDirection(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}
