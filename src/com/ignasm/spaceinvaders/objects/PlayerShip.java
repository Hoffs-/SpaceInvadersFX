package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class PlayerShip extends Ship {
    public PlayerShip() {
        super(ImageLoader.getPlayerImageView().getImage(),
                51, 36,
                Duration.millis(1000));
    }
}
