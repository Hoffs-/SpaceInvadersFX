package com.ignasm.spaceinvaders.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipEntityTest {

    private ShipEntity ship = null;

    @BeforeEach
    void setUp() {
        ship = new PlayerShip();
    }

    @DisplayName("Setting memento")
    @Test
    void setMemento() {
        ShipMemento memento = new ShipMemento(10, 10, false);
        ship.setMemento(memento);
        assertEquals(memento.getX(), ship.getLayoutX());
        assertEquals(memento.getY(), ship.getLayoutY());
        assertEquals(memento.isBlownUp(), ship.isBlownUp());
    }
}