package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class PlayerShip extends ShipEntity {
    public PlayerShip() {
        super(
                ImageLoader.getPlayer(),
                51, 36,
                Duration.millis(1000)
        );
    }
}
