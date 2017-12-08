package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.ShipMemento;

public class SceneMemento {
    private final ShipMemento playerMemento;
    private final ShipMemento[][] enemyMemento;
    private final int score;
    private final int enemyDirection;
    private final int gameSpeed;

    public SceneMemento(ShipMemento player, ShipMemento[][] enemies, int score, int direction, int speed) {
        playerMemento = player;
        enemyMemento = enemies;
        this.score = score;
        enemyDirection = direction;
        gameSpeed = speed;
    }

    public int getEnemyDirection() {
        return enemyDirection;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public ShipMemento getPlayerMemento() {
        return playerMemento;
    }

    public ShipMemento[][] getEnemyMemento() {
        return enemyMemento;
    }

    public int getScore() {
        return score;
    }
}
