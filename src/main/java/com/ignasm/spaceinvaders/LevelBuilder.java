package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.EnemyOne;
import com.ignasm.spaceinvaders.entities.EnemyThree;
import com.ignasm.spaceinvaders.entities.EnemyTwo;
import com.ignasm.spaceinvaders.entities.ShipEntity;
import javafx.scene.layout.Pane;

import java.util.Arrays;

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
        return generateCustomLevel(6, 10, new EnemyThree(), new EnemyTwo(), new EnemyOne());
    }

    public ShipEntity[][] generateCustomLevel(int rows, int columns, ShipEntity... entities) {
        if (entities.length > rows) {
            entities = Arrays.copyOfRange(entities, 0, rows);
        }

        int sliceSize = (int) Math.ceil((double) rows / (double) entities.length);
        ShipEntity[][] enemyEntities = new ShipEntity[rows][columns];

        for (int k = 0; k < entities.length; k++) {
            for (int i = k * sliceSize; i < Math.min(enemyEntities.length, (k * sliceSize) + sliceSize); i++) {
                for (int j = 0; j < enemyEntities[i].length; j++) {
                    enemyEntities[i][j] = new ShipEntity(entities[k].getImage(), entities[k].getEntityWidth(), entities[k].getEntityHeight(), entities[k].getAnimationDuration());
                }
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
