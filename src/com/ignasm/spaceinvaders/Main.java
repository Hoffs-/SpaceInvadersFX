package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.*;
import com.ignasm.spaceinvaders.objects.Entity;
import com.ignasm.spaceinvaders.objects.ShipEntity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

    private ShipEntity[][] enemyEntities = new ShipEntity[6][10];
    private ShipEntity playerEntity;
    private List<ImageView> playerShots = new ArrayList<>();
    private List<Entity> enemyShots = new ArrayList<>();

    private AnimationTimer animationTimer;

    private int pointTracker = 0;

    private Text pointText;
    private String direction = "-";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(gameWindow, 730, 730));
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();


        gameWindow = new Pane();
        gameWindow.prefWidthProperty().bind(primaryStage.getScene().widthProperty());
        gameWindow.prefHeightProperty().bind(primaryStage.getScene().heightProperty());
        gameWindow.setStyle("-fx-background-color: black");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyOne();
            }
        }

        pointText = new Text("Score: " + pointTracker);
        pointText.setFont(new Font("Consolas", 30));
        pointText.setFill(Color.WHITE);

        gameWindow.getChildren().add(pointText);

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyTwo();
            }
        }

        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new EnemyThree();
            }
        }

        playerEntity = new PlayerShip();
        gameWindow.getChildren().add(playerEntity);
        playerEntity.setLayoutX(gameWindow.getPrefWidth() / 2);
        playerEntity.setLayoutY(gameWindow.getPrefHeight() - playerEntity.getEntityHeight() - OFFSET_Y);
        gameWindow.requestFocus();

        gameWindow.setOnKeyPressed(event -> direction = event.getCode().getName().toLowerCase());
        gameWindow.setOnKeyReleased(event -> direction = "-");

        gameWindow.setOnKeyPressed(InputHandler.getInstance()::keyDownHandler);
        gameWindow.setOnKeyReleased(InputHandler.getInstance()::keyUpHandler);


        double y = enemyEntities[0][0].getEntityHeight() + SPACING_X;
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


    }


    public static void main(String[] args) {
        launch(args);
    }
}
