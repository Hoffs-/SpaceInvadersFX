package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.ImageLoader;
import com.ignasm.spaceinvaders.objects.interfaces.Explosive;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public abstract class ShipEntity extends Entity implements Explosive {
    private boolean blownUp = false;

    ShipEntity(Image image, int w, int h, Duration duration) {
        super(image, w, h, duration);
    }

    @Override
    public void blowUp() {
        blownUp = true;
        stopAnimation();
        setImage(ImageLoader.getExplosion());
    }

    @Override
    public boolean isBlownUp() {
        return blownUp;
    }
}
