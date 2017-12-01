package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyShot;
import com.ignasm.spaceinvaders.entities.PlayerShot;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import com.ignasm.spaceinvaders.entities.ShipMemento;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class GameScene {
    private Pane window;
    private ShipEntity[][] enemyEntities; // [6][10]
    private ShipEntity playerEntity;

    private ShotPool playerShots;
    private ShotPool enemyShots;

    private PointTracker pointTracker;
    private SceneMemento lastSave;
    private int gameSpeed;

    public GameScene(Pane gameWindow, ShipEntity[][] enemies, ShipEntity player, PointTracker tracker, int speed) {
        window = gameWindow;
        enemyEntities = enemies;
        playerEntity = player;
        pointTracker = tracker;
        gameSpeed = speed;

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

    public SceneMemento getLastSave() {
        return lastSave;
    }

    public void setLastSave(SceneMemento save) {
        lastSave = save;
    }

    public SceneMemento getMemento() {
        ShipMemento playerMemento = playerEntity.getMemento();
        ShipMemento[][] enemiesMemento = new ShipMemento[enemyEntities.length][enemyEntities[0].length];

        for (int i = 0; i < enemyEntities.length; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemiesMemento[i][j] = enemyEntities[i][j].getMemento();
            }
        }

        return new SceneMemento(playerMemento, enemiesMemento, pointTracker.getPoints());
    }

    public void setMemento(SceneMemento memento) {
        playerEntity.setMemento(memento.getPlayerMemento());
        pointTracker.setPoints(memento.getScore());

        ShipMemento[][] enemies = memento.getEnemyMemento();
        for (int i = 0; i < enemyEntities.length; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                ShipEntity enemy = enemyEntities[i][j];
                enemy.setMemento(enemies[i][j]);
                if (!enemy.isBlownUp() && !window.getChildren().contains(enemy)) {
                    window.getChildren().add(enemy);
                }
            }
        }
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    /*
    public static void spaceOutEnemies(Entity[][]) {

    }*/
}
