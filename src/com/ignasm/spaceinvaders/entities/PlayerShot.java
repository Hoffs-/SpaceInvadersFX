package com.ignasm.spaceinvaders.entities;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class PlayerShot extends Entity {
    public PlayerShot() {
        super(ImageLoader.getPlayerShot(), 6, 20, Duration.millis(0));
    }
}
