package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.EnemyShot;
import com.ignasm.spaceinvaders.objects.PlayerShot;
import com.ignasm.spaceinvaders.objects.ShipEntity;
import javafx.scene.layout.Pane;

public class GameScene {
    private Pane window;
    private ShipEntity[][] enemyEntities; // [6][10]
    private ShipEntity playerEntity;

    private ObjectPool<PlayerShot> playShots;
    private ObjectPool<EnemyShot> enemyShots;

    private ObjectPool<ShipEntity> ships;

    public GameScene(Pane gameWindow, ShipEntity[][] enemies, ShipEntity player) {
        window = gameWindow;
        enemyEntities = enemies;
        playerEntity = player;
    }



}
