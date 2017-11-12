package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.ImageLoader;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class EnemyThree extends Ship {
    public EnemyThree() {
        super(ImageLoader.getAlien3ImageView().getImage(),
                54, 36,
                Duration.millis(1000));
    }
}
