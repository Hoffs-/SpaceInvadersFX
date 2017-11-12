package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Pane gameWindow;

    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;

    private Ship[][] enemyShips = new Ship[6][10];
    private Ship playerShip;
    private List<ImageView> playerShots = new ArrayList<>();
    private List<Ship> enemyShots = new ArrayList<>();

    private AnimationTimer animationTimer;

    private String direction = "-";

    private int pointTracker = 0;

    private Text pointText;


    @Override
    public void start(Stage primaryStage) throws Exception{
        gameWindow = new Pane();
        primaryStage.setScene(new Scene(gameWindow, 730, 730));
        primaryStage.setTitle("Space Invaders");
        gameWindow.setPrefWidth(730);
        gameWindow.setPrefHeight(730);

        gameWindow.setStyle("-fx-background-color: black");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < enemyShips[i].length; j++) {
                enemyShips[i][j] = new EnemyOne();
            }
        }

        pointText = new Text("Score: " + pointTracker);
        pointText.setFont(new Font("Consolas", 30));
        pointText.setFill(Color.WHITE);

        gameWindow.getChildren().add(pointText);

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < enemyShips[i].length; j++) {
                enemyShips[i][j] = new EnemyTwo();
            }
        }

        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < enemyShips[i].length; j++) {
                enemyShips[i][j] = new EnemyThree();
            }
        }

        playerShip = new PlayerShip();
        gameWindow.getChildren().add(playerShip);
        playerShip.setLayoutX(gameWindow.getPrefWidth() / 2);
        playerShip.setLayoutY(gameWindow.getPrefHeight() - playerShip.getShipHeight() - OFFSET_Y);
        gameWindow.requestFocus();

        gameWindow.setOnKeyPressed(event -> direction = event.getCode().getName().toLowerCase());

        gameWindow.setOnKeyReleased(event -> direction = "-");

        double y = enemyShips[0][0].getShipHeight() + SPACING_X;
        for (Ship[] ship : enemyShips) {
            double x = OFFSET_X;
            for (Ship aShip : ship) {
                aShip.setLayoutX(x);
                aShip.setLayoutY(y);
                gameWindow.getChildren().add(aShip);
                x += aShip.getShipWidth() + SPACING_X;
            }
            y += ship[0].getShipHeight() + SPACING_Y;
        }

        animationTimer = new AnimationTimer() {
            double action = 1;
            long playerLastShot = 0;
            long enemyLastShot = 0;

            @Override
            public void handle(long now) {

                if (enemyShips[0][enemyShips[0].length - 1].getShipWidth() + enemyShips[0][enemyShips[0].length - 1].getLayoutX() >= gameWindow.getWidth()) {
                    action = -1;
                }

                if (enemyShips[0][0].getLayoutX() <= 0) {
                    action = 1;
                }

                for (Ship[] ship : enemyShips) {
                    for (Ship aShip : ship) {
                        if (aShip != null) {
                            aShip.setLayoutX(aShip.getLayoutX() + action);
                        }
                    }
                }

                switch (direction) {
                    case "a":
                        playerShip.setLayoutX(playerShip.getLayoutX() - 3);
                        break;
                    case "d":
                        playerShip.setLayoutX(playerShip.getLayoutX() + 3);
                        break;
                    case "g":
                    case "space":
                        if (now - playerLastShot > 1000000000) {
                            playerLastShot = now;

                            ImageView shot = ImageLoader.getPlayerShotImageView();

                            shot.setLayoutX(playerShip.getLayoutX() + (shot.getLayoutBounds().getWidth() / 2) );
                            shot.setLayoutY(playerShip.getLayoutY() - playerShip.getShipHeight() - shot.getLayoutBounds().getHeight() - 5);

                            playerShots.add(shot);
                            gameWindow.getChildren().add(shot);
                        }
                }

                long diff = 750000000;

                if (now - enemyLastShot > diff) {
                    enemyLastShot = now;
                    Ship shot = new EnemyShotSprite();
                    enemyShots.add(shot);
                    gameWindow.getChildren().add(shot);

                    Random rand = new Random();

                    mainLoop:
                    while (true) {
                        int col = rand.nextInt(enemyShips[0].length);
                        int row = enemyShips.length - 1;

                        while (row >= 0) {
                            if (!enemyShips[row][col].isBlownUp()) {
                                shot.setLayoutX(enemyShips[row][col].getLayoutX() + (enemyShips[row][col].getShipWidth() / 2));
                                shot.setLayoutY(enemyShips[row][col].getLayoutY() + enemyShips[row][col].getShipHeight());
                                break mainLoop;
                            }
                            row--;
                        }
                    }
                }

                ListIterator eShotIterator = enemyShots.listIterator();


                Bounds playerShipBounds = new BoundingBox(playerShip.getLayoutX(), playerShip.getLayoutY(), playerShip.getShipWidth(), playerShip.getShipHeight());
                while (eShotIterator.hasNext()) {
                    ImageView shot = (ImageView) eShotIterator.next();
                    shot.setLayoutY(shot.getLayoutY() + 3);
                    if (shot.getLayoutY() > gameWindow.getHeight()) {
                        eShotIterator.remove();
                        gameWindow.getChildren().remove(shot);
                    }

                    Bounds enemyShotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());

                    if (playerShipBounds.intersects(enemyShotBounds)) { // Game over
                        this.stop();
                        Text gameOver = new Text("Game Over");
                        gameOver.setFill(Color.RED);
                        gameOver.setFont(new Font("Consolas", 30));
                        gameOver.setLayoutY(gameWindow.getPrefHeight() / 2);
                        gameOver.setLayoutX((gameWindow.getPrefWidth() / 2) - (gameOver.getLayoutBounds().getWidth() / 2) );
                        gameWindow.getChildren().add(gameOver);
                    }

                }


                ListIterator pShotIterator = playerShots.listIterator();

                while (pShotIterator.hasNext()) {
                    ImageView shot = (ImageView) pShotIterator.next();
                    shot.setLayoutY(shot.getLayoutY() - 5);
                    if (shot.getLayoutY() + shot.getLayoutBounds().getHeight() < 0) {
                        pShotIterator.remove();
                        gameWindow.getChildren().remove(shot);
                    }
                }

                pShotIterator = playerShots.listIterator();

                while (pShotIterator.hasNext()) {
                    ImageView shot = (ImageView) pShotIterator.next();
                    Bounds shotBounds = new BoundingBox(shot.getLayoutX(), shot.getLayoutY(), shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());
                    boolean wasHit = false;

                    startLoop:
                    for (Ship[] rowShips : enemyShips) {
                        for (Ship ship : rowShips) {
                            Bounds shipBounds = new BoundingBox(ship.getLayoutX(), ship.getLayoutY(), ship.getShipWidth(), ship.getShipHeight());
                            if (shipBounds.intersects(shotBounds) && !ship.isBlownUp()) {
                                ship.explode();
                                gameWindow.getChildren().remove(ship);
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

                pointText.setText("Score: " + pointTracker);
                pointText.setLayoutX(gameWindow.getPrefWidth() - pointText.getLayoutBounds().getWidth() - 10);
                pointText.setLayoutY(pointText.getLayoutBounds().getHeight() + 5);

                if (pointTracker == (enemyShips.length * enemyShips[0].length)) {
                    Text winner = new Text("Winner");
                    winner.setFont(new Font("Roboto", 30));
                    winner.setFill(Color.GREEN);
                    winner.setLayoutX((gameWindow.getPrefWidth() / 2) - (winner.getLayoutBounds().getWidth() / 2));
                    winner.setLayoutY(gameWindow.getPrefHeight() / 2);
                    gameWindow.getChildren().add(winner);
                }
            }
        };
        animationTimer.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
