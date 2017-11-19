package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.objects.PlayerShot;
import javafx.scene.layout.Pane;

/**
 * Ignas Maslinskas
 * 20153209
 * PRIf-15/1
 */
public class ShotPool extends ObjectPool<PlayerShot> {
    private Pane window;

    public ShotPool(int size, Pane window) {
        super(size);
        this.window = window;
    }

    @Override
    public PlayerShot acquireObject() {
        PlayerShot shot = objects.stream()
                .filter(playerShot -> playerShot.isOutOfBounds(window))
                .findFirst()
                .orElse(null);
        if (shot == null && getObjectCount() < poolSize) {
            shot = createObject();
            objects.add(shot);
        }
        return shot;
    }

    @Override
    public PlayerShot createObject() {
        return new PlayerShot();
    }
}
