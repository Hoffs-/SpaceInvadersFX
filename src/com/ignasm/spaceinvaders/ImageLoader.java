package com.ignasm.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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

    private final static Image playerShotImage = loadImage("PlayerShot_Sprite_48x24");
    private final static Image explosionImage = loadImage("Explosion_Sprite_36x54");

    public static ImageView getPlayerImageView() {
        return new ImageView(playerImage);
    }

    public static ImageView getAlien1ImageView() {
        return new ImageView(alien1Image);
    }

    public static ImageView getAlien2ImageView() {
        return new ImageView(alien2Image);
    }

    public static ImageView getAlien3ImageView() {
        return new ImageView(alien3Image);
    }

    public static ImageView getSpaceshipImageView() {
        return new ImageView(spaceshipImage);
    }

    public static ImageView getEnemyShotImageView() {
        return new ImageView(enemyShotImage);
    }

    public static ImageView getPlayerShotImageView() {
        return new ImageView(playerShotImage);
    }

    public static ImageView getExplosionImageView() {
        return new ImageView(explosionImage);
    }

    private static Image loadImage(String image) {
        Image img = new Image(String.format("com/ignasm/spaceinvaders/resources/%s.png", image));
        return img;
    }
}
