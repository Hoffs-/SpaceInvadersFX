package com.ignasm.spaceinvaders;

import com.ignasm.spaceinvaders.entities.Entity;
import com.ignasm.spaceinvaders.entities.PlayerShot;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShotPoolTest {
    private ShotPool pool = null;

    @BeforeEach
    void setUp() {
        pool = new ShotPool(new PlayerShot(), 10, new Pane());
    }

    @DisplayName("Acquiring object")
    @Test
    void acquireObject() {
        pool.acquireObject();
        assertEquals(1, pool.getObjectCount());
        assertEquals(0, pool.getReleasedCount());
        assertEquals(1, pool.getActiveCount());
    }

    @DisplayName("Releasing object")
    @Test
    void releaseObject() {
        Entity entity = pool.acquireObject();
        pool.releaseObject(entity);
        assertEquals(1, pool.getObjectCount());
        assertEquals(1, pool.getReleasedCount());
        assertEquals(0, pool.getActiveCount());
    }
}