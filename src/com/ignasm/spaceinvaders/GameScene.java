package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.Ship;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GameScene {
    private Ship[][] enemyShips = new Ship[6][10];
    private Ship playerShip;
    private List<ImageView> playerShots = new ArrayList<>();
    private List<Ship> enemyShots = new ArrayList<>();


}
