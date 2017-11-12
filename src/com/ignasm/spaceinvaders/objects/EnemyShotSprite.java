package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemyShotSprite extends Ship {
    public EnemyShotSprite() {
        super(ImageLoader.getEnemyShotImageView().getImage(),
                10, 20,
                Duration.millis(1000));
    }
}
