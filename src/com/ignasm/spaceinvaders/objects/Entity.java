package com.ignasm.spaceinvaders.objects;

import com.ignasm.spaceinvaders.helpers.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class Entity extends ImageView {
    private Animation objectAnimation;
    private double entityWidth;
    private double entityHeight;

    Entity(Image image, int w, int h, Duration duration) {
        setImage(image);
        entityWidth = w;
        entityHeight = h;
        objectAnimation = setupAnimation(w, h, duration);
        startAnimation();
    }

    private Animation setupAnimation(int width, int height, Duration duration) {
        int columns = (int) (getImage().getWidth() / width);
        int rows = (int) (getImage().getHeight() / height);
        int count = columns * rows;

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

    public void stopAnimation() {
        if (objectAnimation != null) {
            objectAnimation.stop();
        }
    }

    public double getEntityWidth() {
        return entityWidth;
    }

    public double getEntityHeight() {
        return entityHeight;
    }

    public boolean isOutOfBounds(Pane window) {
        Bounds bounds = getLayoutBounds();
        return bounds.getMaxX() < 0 ||
                bounds.getMaxY() < 0 ||
                bounds.getMinX() > window.getWidth() ||
                bounds.getMinY() > window.getHeight();
    }
}