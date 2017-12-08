package com.ignasm.spaceinvaders;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private GameLogic gameLogic;

    public GameLoop(GameLogic logic) {
        gameLogic = logic;
    }

    @Override
    public void handle(long now) {
        gameLogic.updatePlayer(now);
        gameLogic.updateEnemies(now);
        gameLogic.updatePoints();
        gameLogic.checkIfWon();

        if (gameLogic.shouldStop()) {
            this.stop();
        }
    }
}
