package com.ignasm.spaceinvaders.entities;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemyTwo extends ShipEntity {
    public EnemyTwo() {
        super(
                ImageLoader.getAlienTwo(),
                54, 36,
                Duration.millis(1000)
        );
    }
}
