package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.PlayerShip;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane gameWindow = new Pane();

        primaryStage.setScene(new Scene(gameWindow, 730, 730));
        primaryStage.setTitle("Space Invaders");

        gameWindow.setStyle("-fx-background-color: black");
        gameWindow.prefWidthProperty().bind(primaryStage.getScene().widthProperty());
        gameWindow.prefHeightProperty().bind(primaryStage.getScene().heightProperty());

        LevelBuilder levelBuilder = new LevelBuilder(gameWindow);

        ShipEntity playerEntity = new PlayerShip();
        playerEntity.setPosition(gameWindow.getPrefWidth() / 2, gameWindow.getPrefHeight() - playerEntity.getEntityHeight() - LevelBuilder.OFFSET_Y);

        ShipEntity[][] enemyEntities = levelBuilder.generateStandardLevel();

        PointTracker tracker = new PointTracker(gameWindow);

        gameWindow.getChildren().add(playerEntity);
        gameWindow.getChildren().add(tracker);

        GameScene scene = new GameScene(gameWindow, enemyEntities, playerEntity, tracker, 3);
        InputHandler inputHandler = new InputHandler(scene);
        GameLogic logic = new GameLogic(scene, inputHandler);
        GameLoop gameLoop = new GameLoop(logic);

        gameWindow.requestFocus();
        primaryStage.show();
        gameLoop.start();
    }
}
