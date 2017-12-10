package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyOne;
import com.ignasm.spaceinvaders.entities.EnemyThree;
import com.ignasm.spaceinvaders.entities.EnemyTwo;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.scene.layout.Pane;

public class LevelBuilder {
    public static final double OFFSET_X = 50;
    public static final double OFFSET_Y = 12;
    public static final double SPACING_X = 10;
    public static final double SPACING_Y = 10;
    private Pane window;

    public LevelBuilder(Pane parent) {
        window = parent;
    }

    public ShipEntity[][] generateStandardLevel() {
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
        placeEnemies(enemyEntities);
        return enemyEntities;
    }

    public ShipEntity[][] generateCustomLevel(int rows, int columns, ShipEntity entity) {
        ShipEntity[][] enemyEntities = new ShipEntity[rows][columns];
        for (int i = 0; i < enemyEntities.length; i++) {
            for (int j = 0; j < enemyEntities[i].length; j++) {
                enemyEntities[i][j] = new ShipEntity(entity.getImage(), entity.getEntityWidth(), entity.getEntityHeight(), entity.getAnimationDuration());
            }
        }
        placeEnemies(enemyEntities);
        return enemyEntities;
    }

    private void placeEnemies(ShipEntity[][] entities) {
        double y = entities[0][0].getEntityHeight() + OFFSET_Y;
        for (ShipEntity[] entity : entities) {
            double x = OFFSET_X;
            for (ShipEntity aEntity : entity) {
                aEntity.setLayoutX(x);
                aEntity.setLayoutY(y);
                window.getChildren().add(aEntity);
                x += aEntity.getEntityWidth() + SPACING_X;
            }
            y += entity[0].getEntityHeight() + SPACING_Y;
        }
    }
}
