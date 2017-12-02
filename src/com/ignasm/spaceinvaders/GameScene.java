package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyShot;
import com.ignasm.spaceinvaders.entities.PlayerShot;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import com.ignasm.spaceinvaders.entities.ShipMemento;
import com.ignasm.spaceinvaders.helpers.MovementDirection;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScene {
    private Pane window;
    private ShipEntity[][] enemyEntities; // [6][10]
    private ShipEntity playerEntity;

    private ShotPool playerShots;
    private ShotPool enemyShots;

    private PointTracker pointTracker;
    private SceneMemento lastSave;
    private int gameSpeed;

    private boolean isGameOver = false;
    private int enemiesDirection = 1;

    public GameScene(Pane gameWindow, ShipEntity[][] enemies, ShipEntity player, PointTracker tracker, int speed) {
        window = gameWindow;
        enemyEntities = enemies;
        playerEntity = player;
        pointTracker = tracker;
        gameSpeed = speed;

        playerShots = new ShotPool(new PlayerShot(), 100, window);
        enemyShots = new ShotPool(new EnemyShot(), 100, window);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
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

        return new SceneMemento(playerMemento,
                enemiesMemento,
                pointTracker.getPoints(),
                enemiesDirection,
                gameSpeed
        );
    }

    public void setMemento(SceneMemento memento) {
        playerEntity.setMemento(memento.getPlayerMemento());
        pointTracker.setPoints(memento.getScore());
        gameSpeed = memento.getGameSpeed();
        enemiesDirection = memento.getEnemyDirection();

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

    public ShipEntity[] getBottomEnemies() {
        ShipEntity[][] enemies = enemyEntities;
        List<ShipEntity> bottomEnemies = new ArrayList<>();

        for (int column = 0; column < getEnemyColumns(); column++) {
            for (int row = getEnemyRows() - 1; row >= 0; row--) {
                ShipEntity enemy = enemies[row][column];
                if (!enemy.isBlownUp()) {
                    bottomEnemies.add(enemy);
                    break;
                }
            }
        }
        return bottomEnemies.toArray(new ShipEntity[bottomEnemies.size()]);
    }

    public MovementDirection getEnemyDirection() {
        ShipEntity firstShip = enemyEntities[0][0];
        ShipEntity lastShip = enemyEntities[getEnemyRows() - 1][getEnemyColumns() - 1];

        if (firstShip.getMinX() <= 0 || lastShip.getMaxX() >= window.getWidth()) {
            enemiesDirection *= -1;
        }

        return MovementDirection.getByValue(enemiesDirection);
    }
}
