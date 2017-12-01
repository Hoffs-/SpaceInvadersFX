package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.Entity;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import com.ignasm.spaceinvaders.helpers.MovementDirection;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class GameLoop extends AnimationTimer {
    private static final int GAME_SPEED = 3;
    private static final long PLAYER_SHOT_INTERVAL = 1_000_000_000;
    private static final long ENEMY_SHOT_INTERVAL = 750_000_000;

    private long playerLastShot = 0;
    private long enemyLastShot = 0;

    private int enemiesDirection = 1;

    private GameScene scene;

    public GameLoop(GameScene gameScene) {
        scene = gameScene;
    }

    @Override
    public void handle(long now) {
        moveEnemyShips(getMovementDirection());

        handleUserAction(now);

        if (canEnemyShoot(now)) {
            enemyLastShot = now;
            addEnemyShot();
        }

        updateEnemyShots();
        checkEnemyShots();

        updatePlayerShots();
        checkPlayerShots();

        scene.getPointTracker().update();

        if (scene.getEnemiesLeft() == 0) {
            setGameOverText(GameStatus.WON);
        }
    }

    // Maybe move most of the logic to GameLogic class?
    private void updateEnemyShots() {
        scene.getEnemyShots().moveShots(GAME_SPEED);
    }

    private void checkEnemyShots() {
        ShotPool enemyShots = scene.getEnemyShots();
        Entity[] entities = enemyShots.collidesWith(scene.getPlayer());

        if (entities.length > 0) {
            blowUpShip(scene.getPlayer());
            setGameOverText(GameStatus.LOST);
        }
    }

    private void updatePlayerShots() {
        scene.getPlayerShots().moveShots(MovementDirection.UP.getValue() * GAME_SPEED);
    }

    private void checkPlayerShots() {
        List<ShipEntity> enemies = Arrays.stream(scene.getEnemies())
                .flatMap(Arrays::stream)
                .filter(shipEntity -> !shipEntity.isBlownUp())
                .collect(Collectors.toList());
        ShotPool playerShots = scene.getPlayerShots();

        for (ShipEntity enemy : enemies) {
            Entity[] shots = playerShots.collidesWith(enemy);
            if (shots.length > 0) {
                playerShots.releaseObjects(shots);
                scene.getPointTracker().addPoint();
                blowUpShip(enemy);
            }
        }
    }

    private void handleUserAction(long now) {
        Entity player = scene.getPlayer();
        Pane window = scene.getWindow();

        // Maybe use commands? getMovementCommand + getActionCommand(Shooting)? Maybe redundant/unpractical...

        int playerMove = InputHandler.getInstance().getMovementDirection().getValue() * GAME_SPEED;
        scene.getPlayer().moveX(playerMove);

        if (player.isPartiallyOutOfBounds(window)) {
            double newX = (player.getLayoutX() > 0) ? window.getWidth() - player.getEntityWidth() : 0;
            player.setLayoutX(newX);
        }

        if (InputHandler.getInstance().isShooting() && canPlayerShoot(now)) {
            playerLastShot = now;
            addShot(scene.getPlayerShots(), scene.getPlayer());
        }
    }

    private boolean canPlayerShoot(long now) {
        return now - playerLastShot > PLAYER_SHOT_INTERVAL;
    }

    private boolean canEnemyShoot(long now) {
        return now - enemyLastShot > ENEMY_SHOT_INTERVAL;
    }

    private void addEnemyShot() {
        Random rand = new Random();
        ShipEntity[] enemies = getBottomEnemies();
        ShipEntity enemy = enemies[rand.nextInt(enemies.length)];
        addShot(scene.getEnemyShots(), enemy);
    }

    private void addShot(ShotPool pool, ShipEntity from) {
        Entity shot = pool.acquireObject();
        shot.setPosition(from.getMiddleX() - (shot.getEntityWidth() / 2), from.getLayoutY() + from.getEntityHeight());
    }

    private ShipEntity[] getBottomEnemies() {
        ShipEntity[][] enemies = scene.getEnemies();
        List<ShipEntity> bottomEnemies = new ArrayList<>();

        for (int column = 0; column < scene.getEnemyColumns(); column++) {
            for (int row = scene.getEnemyRows() - 1; row >= 0; row--) {
                ShipEntity enemy = enemies[row][column];
                if (!enemy.isBlownUp()) {
                    bottomEnemies.add(enemy);
                    break;
                }
            }
        }
        return bottomEnemies.toArray(new ShipEntity[bottomEnemies.size()]);
    }

    private void setGameOverText(GameStatus status) {
        Pane window = scene.getWindow();
        Text text = new Text(status.getDisplayText());
        text.setFill(status.getDisplayColor());
        text.setFont(new Font("Roboto", 30));
        text.setLayoutX((window.getPrefWidth() / 2) - (text.getLayoutBounds().getWidth() / 2));
        text.setLayoutY(window.getPrefHeight() / 2);
        window.getChildren().add(text);
        this.stop();
    }

    // TODO: Rethink this
    private int getMovementDirection() {
        Pane gameWindow = scene.getWindow();
        ShipEntity firstShip = scene.getEnemies()[0][0];
        ShipEntity lastShip = scene.getEnemies()[scene.getEnemyRows() - 1][scene.getEnemyColumns() - 1];

        if (firstShip.getMinX() <= 0 || lastShip.getMaxX() >= gameWindow.getWidth()) {
            enemiesDirection *= -1;
        }

        return enemiesDirection;
    }

    private void moveEnemyShips(int direction) {
        Arrays.stream(scene.getEnemies()).flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .forEach(ship -> ship.moveX(direction));
    }

    private void blowUpShip(ShipEntity ship) {
        ship.blowUp();
        new Timeline(
                new KeyFrame(
                        Duration.millis(350),
                        event -> scene.getWindow().getChildren().remove(ship)
                )
        ).play();
    }
}
