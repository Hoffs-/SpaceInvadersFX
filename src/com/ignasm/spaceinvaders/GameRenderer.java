package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.Entity;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameRenderer extends AnimationTimer {
    private static final int GAME_SPEED = 3;
    private static final long PLAYER_SHOT_INTERVAL = 1_000_000_000;
    private static final long ENEMY_SHOT_INTERVAL = 750_000_000;

    private long playerLastShot = 0;
    private long enemyLastShot = 0;

    private GameScene gameScene;
    private int enemiesDirection = 1;

    public GameRenderer(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void handle(long now) {
        moveEnemyShips(getMovementDirection());

        handleUserAction(now);

        if (now - enemyLastShot > ENEMY_SHOT_INTERVAL) {
            enemyLastShot = now;
            addEnemyShot();
        }

        updateEnemyShots();
        checkEnemyShots();


        updatePlayerShots();
        checkPlayerShots();

        gameScene.getPointTracker().update();

        /*
        if (pointTracker == (enemyEntities.length * enemyEntities[0].length)) {
            setGameOverText();
        }
        */
    }

    private void checkEnemyShots() {
        ShotPool enemyShots = gameScene.getEnemyShots();
        Entity[] entities = enemyShots.collidesWith(gameScene.getPlayer());
        if (entities.length > 0) {
            setGameOverText();
            this.stop();
        }
    }

    private void updateEnemyShots() {
        gameScene.getEnemyShots().moveShots(GAME_SPEED);
    }

    private void handleUserAction(long now) {
        Entity player = gameScene.getPlayer();
        Pane window = gameScene.getWindow();

        int playerMove = InputHandler.getInstance().getMovementDirection().getValue() * GAME_SPEED;
        gameScene.getPlayer().moveX(playerMove);

        if (player.isPartiallyOutOfBounds(window)) {
            double newX = (player.getLayoutX() > 0) ? window.getWidth() - player.getEntityWidth() : 0;
            player.setLayoutX(newX);
        }

        if (InputHandler.getInstance().isShooting() && canShoot(now)) {
            playerLastShot = now;
            addPlayerShot();
        }
    }

    private boolean canShoot(long now) {
        return now - playerLastShot > PLAYER_SHOT_INTERVAL;
    }

    private void addPlayerShot() {
        ShipEntity player = gameScene.getPlayer();
        Entity shot = gameScene.getPlayerShots().acquireObject();
        shot.setPosition(player.getMiddleX() - (shot.getEntityWidth() / 2), player.getLayoutY() - shot.getEntityHeight());
    }

    // TODO: Fix this
    private void addEnemyShot() {
        ShipEntity[][] enemies = gameScene.getEnemies();
        Entity shot = gameScene.getEnemyShots().acquireObject();

        Random rand = new Random();

        boolean shouldContinue = true;
        while (shouldContinue) {
            int col = rand.nextInt(enemies[0].length);
            int row = enemies.length - 1;

            for (int i = row; i >= 0; i--) {
                ShipEntity enemy = enemies[row][col];

                if (!enemy.isBlownUp()) {
                    shot.setPosition(enemy.getMiddleX() - (shot.getEntityWidth() / 2), enemy.getLayoutY() + enemy.getEntityHeight());
                    shouldContinue = false;
                    break;
                }
            }
        }
    }

    // TODO: FIX THIS
    private void setGameOverText() {
        Pane gameWindow = gameScene.getWindow();
        Text winnerText = new Text("Winner");
        winnerText.setFill(Color.GREEN);
        winnerText.setFont(new Font("Roboto", 30));
        winnerText.setLayoutX((gameWindow.getPrefWidth() / 2) - (winnerText.getLayoutBounds().getWidth() / 2));
        winnerText.setLayoutY(gameWindow.getPrefHeight() / 2);
        gameWindow.getChildren().add(winnerText);
    }

    private void checkPlayerShots() {
        List<ShipEntity> enemies = Arrays.stream(gameScene.getEnemies())
                .flatMap(Arrays::stream)
                .filter(shipEntity -> !shipEntity.isBlownUp())
                .collect(Collectors.toList());
        ShotPool playerShots = gameScene.getPlayerShots();

        for (ShipEntity enemy : enemies) {
            Entity[] shots = playerShots.collidesWith(enemy);
            if (shots.length > 0) {
                playerShots.releaseObjects(shots);
                enemy.blowUp();

                gameScene.getPointTracker().addPoint();

                new Timeline(
                        new KeyFrame(
                                Duration.millis(350),
                                event -> gameScene.getWindow().getChildren().remove(enemy)
                        )
                ).play();
            }
        }
    }

    private void updatePlayerShots() {
        gameScene.getPlayerShots().moveShots(-1 * GAME_SPEED);
    }

    // TODO: Rethink this
    private int getMovementDirection() {
        Pane gameWindow = gameScene.getWindow();
        ShipEntity firstShip = gameScene.getEnemies()[0][0];
        ShipEntity lastShip = gameScene.getEnemies()[gameScene.getEnemyRows() - 1][gameScene.getEnemyColumns() - 1];

        if (firstShip.getMinX() <= 0 || lastShip.getMaxX() >= gameWindow.getWidth()) {
            enemiesDirection *= -1;
        }

        return enemiesDirection;
    }

    private void moveEnemyShips(int direction) {
        for (ShipEntity[] entity : gameScene.getEnemies()) {
            for (ShipEntity aEntity : entity) {
                if (aEntity != null) {
                    aEntity.setLayoutX(aEntity.getLayoutX() + direction);
                }
            }
        }
    }
}
