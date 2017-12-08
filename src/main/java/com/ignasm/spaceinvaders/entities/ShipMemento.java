package com.ignasm.spaceinvaders.entities;

public class ShipMemento {
    private double x, y;
    private boolean isBlownUp;

    public ShipMemento(double x, double y, boolean isBlown) {
        this.x = x;
        this.y = y;
        this.isBlownUp = isBlown;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isBlownUp() {
        return isBlownUp;
    }
}
