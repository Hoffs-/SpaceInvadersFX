package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyShot;
import com.ignasm.spaceinvaders.entities.PlayerShot;
import com.ignasm.spaceinvaders.entities.Entity;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GameRenderer extends AnimationTimer {
    private static final int SHOT_SPEED = 3;
    private static final int PLAYER_SHOT_INTERVAL = 1000000000;
    private static final long ENEMY_SHOT_INTERVAL = 750000000;
    
    private double action = 1;
    private long playerLastShot = 0;
    private long enemyLastShot = 0;

    private Pane gameWindow;

    private String userInput = "-";

    private int pointTracker = 0;

    private Text pointText = new Text();

    private GameScene gameScene;

    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;

    // private ShipEntity[][] enemyEntities = new ShipEntity[6][10];
    private Entity playerEntity;
    private List<Entity> playerShots = new ArrayList<>();
    private List<Entity> enemyShots = new ArrayList<>();

    public GameRenderer(GameScene gameScene) {
        this.gameScene = gameScene;
        this.gameWindow = gameScene.getWindow();
        setupPointText();
    }

    private void setupPointText() {
        pointText = new Text("Score: " + pointTracker);
        pointText.setFont(new Font("Consolas", 30));
        pointText.setFill(Color.WHITE);
        gameWindow.getChildren().add(pointText);
    }

    @Override
    public void handle(long now) {
        action = getMovementDirection();
        moveEnemyShips();

        handleUserAction(now);

        if (now - enemyLastShot > ENEMY_SHOT_INTERVAL) {
            enemyLastShot = now;
            addEnemyShot();
        }

        updateEnemyShots();


        updatePlayerShots();
        // checkPlayerShots();

        updateScore();

        /*
        if (pointTracker == (enemyEntities.length * enemyEntities[0].length)) {
            setGameOverText();
        }
        */
    }

    private void updateEnemyShots() {
        gameScene.getEnemyShots().moveShots(SHOT_SPEED);

        /*
        if (checkEnemyHit(shot)) { // Game over
            setGameOverText();
            this.stop();
        }
        */
    }

    private boolean isOutOfBounds(ImageView object) {
        return object.getLayoutY() > gameWindow.getHeight() || object.getLayoutX() > gameWindow.getWidth()
                || object.getLayoutY() < 0 || object.getLayoutX() < 0;
    }

    private boolean checkEnemyHit(ImageView shot) {
        Bounds playerShipBounds = new BoundingBox(playerEntity.getLayoutX(), playerEntity.getLayoutY(), playerEntity.getEntityWidth(), playerEntity.getEntityHeight());
        Bounds enemyShotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());
        return playerShipBounds.intersects(enemyShotBounds);
    }

    private void handleUserAction(long now) {
        switch (userInput) {
            case "a":
                playerEntity.setLayoutX(playerEntity.getLayoutX() - 3);
                break;
            case "d":
                playerEntity.setLayoutX(playerEntity.getLayoutX() + 3);
                break;
            case "g":
            case "space":
                if (now - playerLastShot > PLAYER_SHOT_INTERVAL) {
                    playerLastShot = now;
                    addPlayerShot();
                }
        }
    }

    private void addPlayerShot() {
        Entity shot = new PlayerShot();

        shot.setLayoutX(playerEntity.getLayoutX() + (shot.getLayoutBounds().getWidth() / 2) );
        shot.setLayoutY(playerEntity.getLayoutY() - playerEntity.getEntityHeight() - shot.getLayoutBounds().getHeight() - 5);

        playerShots.add(shot);
        gameWindow.getChildren().add(shot);
    }

    private void addEnemyShot() {
        ShipEntity[][] enemyEntities = gameScene.getEnemyEntities();

        Entity shot = new EnemyShot();
        enemyShots.add(shot);
        gameWindow.getChildren().add(shot);

        Random rand = new Random();

        boolean shouldContinue = true;
        while (shouldContinue) {
            int col = rand.nextInt(enemyEntities[0].length);
            int row = enemyEntities.length - 1;

            while (row >= 0) {
                if (!enemyEntities[row][col].isBlownUp()) {
                    shot.setLayoutX(enemyEntities[row][col].getLayoutX() + (enemyEntities[row][col].getEntityWidth() / 2));
                    shot.setLayoutY(enemyEntities[row][col].getLayoutY() + enemyEntities[row][col].getEntityHeight());
                    shouldContinue = false;
                    break;
                }
                row--;
            }
        }
    }

    private void setGameOverText() {
        Text winnerText = new Text("Winner");
        winnerText.setFill(Color.GREEN);
        winnerText.setFont(new Font("Roboto", 30));
        winnerText.setLayoutX((gameWindow.getPrefWidth() / 2) - (winnerText.getLayoutBounds().getWidth() / 2));
        winnerText.setLayoutY(gameWindow.getPrefHeight() / 2);
        gameWindow.getChildren().add(winnerText);
    }

    private void updateScore() {
        pointText.setText("Score: " + pointTracker);
        pointText.setLayoutX(gameWindow.getPrefWidth() - pointText.getLayoutBounds().getWidth() - 10);
        pointText.setLayoutY(pointText.getLayoutBounds().getHeight() + 5);
    }

    private void checkPlayerShots() {
        ListIterator pShotIterator = playerShots.listIterator();
        while (pShotIterator.hasNext()) {
            Entity shot = (Entity) pShotIterator.next();
            Bounds shotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());
            boolean wasHit = false;

            startLoop:
            for (ShipEntity[] rowEntities : gameScene.getEnemyEntities()) {
                for (ShipEntity entity : rowEntities) {
                    Bounds shipBounds = new BoundingBox(entity.getLayoutX(), entity.getLayoutY(), entity.getEntityWidth(), entity.getEntityHeight());
                    if (shipBounds.intersects(shotBounds) && !entity.isBlownUp()) {
                        entity.blowUp();
                        gameWindow.getChildren().remove(entity);
                        wasHit = true;
                        pointTracker++;
                        break startLoop;
                    }
                }
            }

            if (wasHit) {
                pShotIterator.remove();
                gameWindow.getChildren().remove(shot);
            }
        }
    }

    private void updatePlayerShots() {
        gameScene.getPlayerShots().moveShots(-1 * SHOT_SPEED);
    }

    private int getMovementDirection() {
        if (gameScene.getEnemyEntities()[gameScene.getEnemyRows() - 1][gameScene.getEnemyColumns() - 1].getLayoutBounds().getMaxX() >= gameWindow.getWidth()) {
            return -1;
        } else {
            return 1;
        }
    }

    private void moveEnemyShips() {
        for (ShipEntity[] entity : gameScene.getEnemyEntities()) {
            for (ShipEntity aEntity : entity) {
                if (aEntity != null) {
                    aEntity.setLayoutX(aEntity.getLayoutX() + action);
                }
            }
        }
    }
}
