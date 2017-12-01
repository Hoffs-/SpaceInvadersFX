package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyShot;
import com.ignasm.spaceinvaders.entities.PlayerShot;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class GameScene {
    private Pane window;
    private ShipEntity[][] enemyEntities; // [6][10]
    private ShipEntity playerEntity;

    private ShotPool playerShots;
    private ShotPool enemyShots;

    private PointTracker pointTracker;

    public GameScene(Pane gameWindow, ShipEntity[][] enemies, ShipEntity player, PointTracker tracker) {
        window = gameWindow;
        enemyEntities = enemies;
        playerEntity = player;
        pointTracker = tracker;


        playerShots = new ShotPool(new PlayerShot(), 100, window);
        enemyShots = new ShotPool(new EnemyShot(), 100, window);
    }

    public PointTracker getPointTracker() {
        return pointTracker;
    }

    public Pane getWindow() {
        return window;
    }

    public ShotPool getPlayerShots() {
        return playerShots;
    }

    public ShotPool getEnemyShots() {
        return enemyShots;
    }

    public ShipEntity[][] getEnemies() {
        return enemyEntities;
    }

    public ShipEntity getPlayer() {
        return playerEntity;
    }

    public int getEnemyRows() {
        return enemyEntities.length;
    }

    public int getEnemyColumns() {
        return (enemyEntities.length > 0) ? enemyEntities[0].length : 0;
    }

    public int getEnemiesLeft() {
        return (int) Arrays.stream(enemyEntities)
                .flatMap(Arrays::stream)
                .filter(enemy -> !enemy.isBlownUp())
                .count();
    }

    /*
    public static void spaceOutEnemies(Entity[][]) {

    }*/
}
