package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.game.objects.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {
    public Pane gameWindow;

    private Ship[][] ships = new Ship[6][10];
    private Ship playerShip;
    private final double OFFSET_X = 50;
    private final double OFFSET_Y = 12;
    private final double SPACING_X = 10;
    private final double SPACING_Y = 10;

    private AnimationTimer animationTimer;

    private String direction = "-";


    @FXML
    void initialize() {
        /*
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
            System.out.println("pressed");
            direction = event.getCharacter();
        });


        gameWindow.setOnKeyReleased(event -> {
            System.out.println("released");
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

                // System.out.println(direction);

                switch (direction) {
                    case "w":
                        break;
                    case "a":
                        break;
                    case "s":
                        break;
                    case "d":
                        break;
                }
            }
        };
        animationTimer.start();
        */
    }

    public void doSomething(KeyEvent keyEvent) {
        System.out.println("hmm");
    }
}
