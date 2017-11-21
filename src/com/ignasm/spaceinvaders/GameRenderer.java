package com.ignasm.spaceinvaders;

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
import java.util.Random;

public class GameRenderer extends AnimationTimer {
    private static final int SHOT_SPEED = 3;
    private static final long PLAYER_SHOT_INTERVAL = 1_000_000_000;
    private static final long ENEMY_SHOT_INTERVAL = 750_000_000;

    private int direction = 1;
    private long playerLastShot = 0;
    private long enemyLastShot = 0;

    private Pane gameWindow;

    private int pointTracker = 0;

    private Text pointText = new Text();

    private GameScene gameScene;

    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;

    // private ShipEntity[][] enemyEntities = new ShipEntity[6][10];
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
        moveEnemyShips(getMovementDirection());

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
        Entity player = gameScene.getPlayer();
        Bounds playerShipBounds = new BoundingBox(player.getLayoutX(), player.getLayoutY(), player.getEntityWidth(), player.getEntityHeight());
        Bounds enemyShotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());
        return playerShipBounds.intersects(enemyShotBounds);
    }

    private void handleUserAction(long now) {
        int playerMove = InputHandler.getInstance().getMovementDirection().getValue() * 3;
        gameScene.getPlayer().moveX(playerMove);

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
        /*
        ListIterator pShotIterator = playerShots.listIterator();
        while (pShotIterator.hasNext()) {
            Entity shot = (Entity) pShotIterator.next();
            Bounds shotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());
            boolean wasHit = false;

            startLoop:
            for (ShipEntity[] rowEntities : gameScene.getEnemies()) {
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
        }*/
    }

    private void updatePlayerShots() {
        gameScene.getPlayerShots().moveShots(-1 * SHOT_SPEED);
    }

    private int getMovementDirection() {
        ShipEntity firstShip = gameScene.getEnemies()[0][0];
        ShipEntity lastShip = gameScene.getEnemies()[gameScene.getEnemyRows() - 1][gameScene.getEnemyColumns() - 1];

        if (firstShip.getMinX() <= 0 || lastShip.getMaxX() >= gameWindow.getWidth()) {
            direction *= -1;
        }

        return direction;
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
