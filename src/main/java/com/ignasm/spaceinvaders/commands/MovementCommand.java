package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.entities.ShipEntity;
import com.ignasm.spaceinvaders.helpers.MovementDirection;

public class MovementCommand implements Command {
    private ShipEntity ship;
    private int speed;
    private MovementDirection direction;

    public MovementCommand(ShipEntity ship, MovementDirection direction, int speed) {
        this.ship = ship;
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void execute() {
        ship.moveX(direction.getValue() * speed);
    }
}
