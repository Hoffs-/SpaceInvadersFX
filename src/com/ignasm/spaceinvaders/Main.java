package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;


    @Override
    public void start(Stage primaryStage) {
        Pane gameWindow = new Pane();

        primaryStage.setScene(new Scene(gameWindow, 730, 730));
        primaryStage.setTitle("Space Invaders");

        gameWindow.setStyle("-fx-background-color: black");
        gameWindow.prefWidthProperty().bind(primaryStage.getScene().widthProperty());
        gameWindow.prefHeightProperty().bind(primaryStage.getScene().heightProperty());

        ShipEntity playerEntity = new PlayerShip();
        playerEntity.setPosition(gameWindow.getPrefWidth() / 2, gameWindow.getPrefHeight() - playerEntity.getEntityHeight() - OFFSET_Y);

        ShipEntity[][] enemyEntities = getEnemyShipEntities();
        double y = enemyEntities[0][0].getEntityHeight() + SPACING_Y;
        for (ShipEntity[] entity : enemyEntities) {
            double x = OFFSET_X;
            for (ShipEntity aEntity : entity) {
                aEntity.setLayoutX(x);
                aEntity.setLayoutY(y);
                gameWindow.getChildren().add(aEntity);
                x += aEntity.getEntityWidth() + SPACING_X;
            }
            y += entity[0].getEntityHeight() + SPACING_Y;
        }

        PointTracker tracker = new PointTracker(gameWindow);

        gameWindow.getChildren().add(playerEntity);
        gameWindow.getChildren().add(tracker);

        GameScene scene = new GameScene(gameWindow, enemyEntities, playerEntity, tracker, 3);
        InputHandler inputHandler = new InputHandler(scene);
        GameLoop gameLoop = new GameLoop(scene, inputHandler);

        gameWindow.requestFocus();
        primaryStage.show();
        gameLoop.start();
    }

    private ShipEntity[][] getEnemyShipEntities() {
        ShipEntity[][] enemyEntities = new ShipEntity[6][10];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyThree();
            }
        }

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyTwo();
            }
        }

        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyOne();
            }
        }
        return enemyEntities;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
