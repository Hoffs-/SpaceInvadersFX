package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemySpaceship extends ShipEntity {
    public EnemySpaceship() {
        super(
                ImageLoader.getBigSpaceship(),
                192, 84,
                Duration.millis(1000)
        );
    }
}
