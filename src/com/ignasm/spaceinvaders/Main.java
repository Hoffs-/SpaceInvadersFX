package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.game.objects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    public Pane gameWindow;

    private Ship[][] ships = new Ship[6][10];
    private Ship playerShip;
    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;

    private LinkedList<ImageView> playerShots = new LinkedList<>();

    private AnimationTimer animationTimer;

    private String direction = "-";


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
        // primaryStage.setTitle("Space Invaders");
        gameWindow = new Pane();
        primaryStage.setScene(new Scene(gameWindow, 730, 730));

        gameWindow.setPrefWidth(730);
        gameWindow.setPrefHeight(730);

        gameWindow.setStyle("-fx-background-color: black");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                ships[i][j] = new EnemyOne();
            }
        }

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                ships[i][j] = new EnemyTwo();
            }
        }

        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                ships[i][j] = new EnemyThree();
            }
        }

        playerShip = new PlayerShip();
        gameWindow.getChildren().add(playerShip);
        System.out.println(gameWindow.getPrefWidth());
        playerShip.setLayoutX(gameWindow.getPrefWidth() / 2);
        playerShip.setLayoutY(gameWindow.getPrefHeight() - playerShip.getShipHeight() - OFFSET_Y);

        gameWindow.requestFocus();

        gameWindow.setOnKeyPressed(event -> {
            direction = event.getCode().getName().toLowerCase();
        });


        gameWindow.setOnKeyReleased(event -> {
            direction = "-";
        });

        double y = ships[0][0].getShipHeight() + SPACING_X;
        for (Ship[] ship : ships) {
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
            long lastShot = 0;

            @Override
            public void handle(long now) {

                if (ships[0][ships[0].length - 1].getShipWidth() + ships[0][ships[0].length - 1].getLayoutX() >= gameWindow.getWidth()) {
                    action = -1;
                }

                if (ships[0][0].getLayoutX() <= 0) {
                    action = 1;
                }

                for (Ship[] ship : ships) {
                    for (Ship aShip : ship) {
                        aShip.setLayoutX(aShip.getLayoutX() + action);
                    }
                }

                switch (direction) {
                    case "a":
                        playerShip.setLayoutX(playerShip.getLayoutX() - 1);
                        break;
                    case "d":
                        playerShip.setLayoutX(playerShip.getLayoutX() + 1);
                        break;
                    case "space":
                        if (now - lastShot > 1000000000) {
                            lastShot = now;


                            ImageView shot = ImageLoader.getPlayerShotImageView();



                            shot.setLayoutX(playerShip.getLayoutX() - (shot.getLayoutBounds().getWidth() / 2) );
                            shot.setLayoutY(playerShip.getLayoutY() - playerShip.getShipHeight() - shot.getLayoutBounds().getHeight() - 5);

                            playerShots.add(shot);
                            gameWindow.getChildren().add(shot);
                        }
                }

                for (ImageView shot : playerShots) { // Use iterator to be able to remove objects while passing through the list
                    shot.setLayoutY(shot.getLayoutY() - 5);
                }

                for (ImageView shot : playerShots) {

                    double topLeftShotX = shot.getLayoutX();
                    double topLeftShotY = shot.getLayoutY();
                    Bounds shotBounds = new BoundingBox(topLeftShotX, topLeftShotY, shot.getLayoutBounds().getWidth(), shot.getLayoutBounds().getHeight());

                    for (Ship[] rowShips : ships) {
                        for (Ship ship : rowShips) {
                            Bounds shipBounds = new BoundingBox(ship.getLayoutX(), ship.getLayoutY(), ship.getShipWidth(), ship.getShipHeight());
                            if (shipBounds.intersects(shotBounds)) {
                                ship.explode();
                            }
                        }
                    }
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
