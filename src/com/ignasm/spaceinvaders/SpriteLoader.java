package com.ignasm.spaceinvaders;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class SpriteLoader {

    public static Animation getSprite(ImageView imageView) {
        Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                2, 2,
                0, 0,
                96, 64
        );
        return animation;
    }
}
