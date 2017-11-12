package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.ImageLoader;
import com.ignasm.spaceinvaders.SpriteAnimation;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public abstract class Ship extends ImageView {
    private Animation objectAnimation;
    private boolean blownUp = false;
    protected double shipWidth = 54;
    protected double shipHeight = 36;

    Ship(Image image, int w, int h, Duration duration) {
        this.setImage(image);
        objectAnimation = this.setupAnimation(w, h, duration);

        this.startAnimation();
    }

    private Animation setupAnimation(int width, int height, Duration duration) {
        Image image = this.getImage();
        int columns = (int) (image.getWidth() / width);
        int count = columns * ((int) (image.getHeight() / height));

        if (count == 1) return null;

        return new SpriteAnimation(
                this,
                duration,
                count, columns,
                0, 0,
                width, height
        );
    }

    public void startAnimation() {
        if (objectAnimation != null) {
            objectAnimation.setCycleCount(-1);
            objectAnimation.play();
        }
    }

    public void pauseAnimation() {
        if (objectAnimation != null) {
            objectAnimation.pause();
        }
    }

    public void explode() {
        if (objectAnimation != null) {
            objectAnimation.stop();
        }
        this.setImage(ImageLoader.getExplosionImageView().getImage());
        blownUp = true;
    }

    public boolean isBlownUp() {
        return blownUp;
    }

    public double getShipWidth() {
        return shipWidth;
    }

    public double getShipHeight() {
        return shipHeight;
    }
}
