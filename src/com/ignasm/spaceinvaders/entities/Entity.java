package com.ignasm.spaceinvaders.entities;

import com.ignasm.spaceinvaders.helpers.SpriteAnimation;
import javafx.animation.Animation;
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
    private Duration animationDuration;
    private int entityWidth;
    private int entityHeight;

    public Entity(Image image, int w, int h, Duration duration) {
        setImage(image);
        entityWidth = w;
        entityHeight = h;
        animationDuration = duration;

        objectAnimation = setupAnimation(w, h, duration);
        startAnimation();
    }

    public Duration getAnimationDuration() {
        return animationDuration;
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

    public int getEntityWidth() {
        return entityWidth;
    }

    public int getEntityHeight() {
        return entityHeight;
    }

    public boolean isOutOfBounds(Pane window) {
        return getMaxX() < 0 ||
                getLayoutY() + getEntityWidth() < 0 ||
                getLayoutX() > window.getWidth() ||
                getLayoutY() > window.getHeight();
    }

    public void moveX(int move) {
        setLayoutX(getLayoutX() + move);
    }

    public void moveY(int move) {
        setLayoutY(getLayoutY() + move);
    }

    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    public double getMaxX() {
        return getLayoutX() + getEntityWidth();
    }

    public double getMinX() {
        return getLayoutX();
    }

    public double getMiddleX() {
        return getLayoutX() + (getEntityWidth() / 2);
    }
}
