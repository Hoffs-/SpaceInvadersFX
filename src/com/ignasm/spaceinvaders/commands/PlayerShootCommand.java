package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.ShotPool;
import com.ignasm.spaceinvaders.entities.ShipEntity;

public class PlayerShootCommand extends PlayerActionCommand {
    private ShotPool pool;

    public PlayerShootCommand(ShipEntity entity, ShotPool playerShotPool) {
        super(entity);
        pool = playerShotPool;
    }

    @Override
    public void execute() {
        ShotPool.addShot(pool, getPlayer());
    }
}
