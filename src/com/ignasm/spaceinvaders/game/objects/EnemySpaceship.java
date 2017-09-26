package com.ignasm.spaceinvaders.game.objects;

import com.ignasm.spaceinvaders.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemySpaceship extends Ship {
    public EnemySpaceship() {
        super(ImageLoader.getSpaceshipImageView().getImage(),
                192, 84,
                Duration.millis(1000));
    }
}
