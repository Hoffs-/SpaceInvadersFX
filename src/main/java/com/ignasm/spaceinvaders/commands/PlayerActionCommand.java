package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.entities.ShipEntity;

abstract public class PlayerActionCommand implements Command {
    private ShipEntity player;

    public PlayerActionCommand(ShipEntity entity) {
        this.player = entity;
    }

    public ShipEntity getPlayer() {
        return player;
    }
}
