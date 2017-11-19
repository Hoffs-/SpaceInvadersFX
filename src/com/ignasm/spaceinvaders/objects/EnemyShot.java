package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemyShot extends Entity {
    public EnemyShot() {
        super(ImageLoader.getEnemyShot(),
                10, 20,
                Duration.millis(1000));
    }
}
