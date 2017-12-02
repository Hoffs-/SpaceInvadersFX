package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.commands.Command;
import com.ignasm.spaceinvaders.entities.Entity;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import com.ignasm.spaceinvaders.helpers.MovementDirection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameLogic {
    private static final long ENEMY_SHOT_INTERVAL = 750_000_000;
    private GameScene scene;
    private InputHandler input;
    private int gameSpeed = 3;
    private long enemyLastShot = 0;

    public GameLogic(GameScene gameScene, InputHandler inputHandler, int initialSpeed) {
        scene = gameScene;
        input = inputHandler;
        gameSpeed = initialSpeed;
    }

    public boolean shouldStop() {
        return scene.isGameOver();
    }

    public void updatePoints() {
        scene.getPointTracker().update();
    }

    public void checkIfWon() {
        if (scene.getEnemiesLeft() == 0) {
            setGameOverText(GameStatus.WON);
            scene.setGameOver(true);
        }
    }

    public void updateEnemies(long now) {
        moveEnemyShips(scene.getEnemyDirection());
        scene.getEnemyShots().moveShots(MovementDirection.DOWN.getValue() * gameSpeed);

        if (canEnemyShoot(now)) {
            enemyLastShot = now;
            addEnemyShot();
        }
        checkEnemyShots();
    }

    private void moveEnemyShips(MovementDirection direction) {
        Arrays.stream(scene.getEnemies()).flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .forEach(ship -> ship.moveX(direction.getValue()));
    }

    private boolean canEnemyShoot(long now) {
        return now - enemyLastShot > ENEMY_SHOT_INTERVAL;
    }

    private void checkEnemyShots() {
        int collisionCount = checkCollisions(scene.getEnemyShots(), new ShipEntity[]{scene.getPlayer()});

        if (collisionCount > 0) {
            setGameOverText(GameStatus.LOST);
            scene.setGameOver(true);
        }
    }

    private void addEnemyShot() {
        Random rand = new Random();
        ShipEntity[] enemies = scene.getBottomEnemies();
        ShipEntity enemy = enemies[rand.nextInt(enemies.length)];
        ShotPool.addShot(scene.getEnemyShots(), enemy);
    }

    public void updatePlayer(long now) {
        executePlayerCommands(now);
        fixPlayerPosition();
        scene.getPlayerShots().moveShots(MovementDirection.UP.getValue() * gameSpeed);
        checkPlayerShots();
    }

    private void executePlayerCommands(long now) {
        Arrays.stream(input.getCommands(now)).forEach(Command::execute);
    }

    private void checkPlayerShots() {
        ShipEntity[] enemies = Arrays.stream(scene.getEnemies())
                .flatMap(Arrays::stream)
                .filter(shipEntity -> !shipEntity.isBlownUp())
                .toArray(ShipEntity[]::new);

        int collisionCount = checkCollisions(scene.getPlayerShots(), enemies);
        scene.getPointTracker().addPoints(collisionCount);
    }

    private int checkCollisions(ShotPool pool, ShipEntity[] ships) {
        int collisionCount = 0;
        for (ShipEntity ship : ships) {
            if (ship.isBlownUp()) continue;

            Entity[] collisionShots = pool.collidesWith(ship);
            if (collisionShots.length > 0) {
                pool.releaseObjects(collisionShots);
                collisionCount += 1;
                blowUpShip(ship);
            }
        }
        return collisionCount;
    }

    private void setGameOverText(GameStatus status) {
        Pane window = scene.getWindow();
        Text text = new Text(status.getDisplayText());
        text.setFill(status.getDisplayColor());
        text.setFont(new Font("Roboto", 30));
        text.setLayoutX((window.getPrefWidth() / 2) - (text.getLayoutBounds().getWidth() / 2));
        text.setLayoutY(window.getPrefHeight() / 2);
        window.getChildren().add(text);
    }

    private void fixPlayerPosition() {
        Entity player = scene.getPlayer();
        Pane window = scene.getWindow();

        if (player.isPartiallyOutOfBounds(window)) {
            double newX = (player.getLayoutX() > 0) ? window.getWidth() - player.getEntityWidth() : 0;
            player.setLayoutX(newX);
        }
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
