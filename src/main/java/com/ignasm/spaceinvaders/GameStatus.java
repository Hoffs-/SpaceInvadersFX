package com.ignasm.spaceinvaders;

import javafx.scene.paint.Color;

public enum GameStatus {
    LOST("You lost!", Color.RED),
    WON("You won!", Color.GREEN);

    private final String displayText;
    private final Color displayColor;

    GameStatus(String s, Color c) {
        this.displayText = s;
        this.displayColor = c;
    }

    public Color getDisplayColor() {
        return displayColor;
    }

    public String getDisplayText() {
        return displayText;
    }
}
