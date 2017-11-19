package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemyThree extends ShipEntity {
    public EnemyThree() {
        super(
                ImageLoader.getAlienThree(),
                54, 36,
                Duration.millis(1000)
        );
    }
}
