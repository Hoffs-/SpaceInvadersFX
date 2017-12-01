package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.ShipMemento;

public class SceneMemento {
    private final ShipMemento playerMemento;
    private final ShipMemento[][] enemyMemento;
    private final int score;

    public SceneMemento(ShipMemento player, ShipMemento[][] enemies, int score) {
        playerMemento = player;
        enemyMemento = enemies;
        this.score = score;
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
