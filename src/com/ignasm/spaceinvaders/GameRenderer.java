package com.ignasm.spaceinvaders;

import javafx.animation.AnimationTimer;

public class GameRenderer extends AnimationTimer {
    double action = 1;
    long playerLastShot = 0;
    long enemyLastShot = 0;

    private GameScene gameScene;

    public GameRenderer(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void handle(long now) {

    }
}
