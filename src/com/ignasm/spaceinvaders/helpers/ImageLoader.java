package com.ignasm.spaceinvaders.helpers;

import javafx.scene.image.Image;


/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class ImageLoader {
    private final static Image alien1Image = loadImage("Alien1_Sprite_36x54");
    private final static Image alien2Image = loadImage("Alien2_Sprite_36x54");
    private final static Image alien3Image = loadImage("Alien3_Sprite_36x54");

    private final static Image spaceshipImage = loadImage("EnemySpaceShip_Sprite_84x192");
    private final static Image enemyShotImage = loadImage("EnemyShot_Sprite_20x10");
    private final static Image playerImage = loadImage("Player_Sprite_36x51");

    private final static Image playerShotImage = loadImage("PlayerShot_Sprite_20x6");
    private final static Image explosionImage = loadImage("Explosion_Sprite_36x54");

    public static Image getAlienOne() {
        return alien1Image;
    }

    public static Image getAlienTwo() {
        return alien2Image;
    }

    public static Image getAlienThree() {
        return alien3Image;
    }

    public static Image getBigSpaceship() {
        return spaceshipImage;
    }

    public static Image getEnemyShot() {
        return enemyShotImage;
    }

    public static Image getPlayer() {
        return playerImage;
    }

    public static Image getPlayerShot() {
        return playerShotImage;
    }

    public static Image getExplosion() {
        return explosionImage;
    }

    private static Image loadImage(String image) {
        return new Image(
                ImageLoader.class.getResourceAsStream(
                        String.format("/com/ignasm/spaceinvaders/resources/%s.png", image)
                )
        );
    }
}
