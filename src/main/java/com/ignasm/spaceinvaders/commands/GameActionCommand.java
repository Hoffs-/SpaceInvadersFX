package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.GameScene;

abstract public class GameActionCommand implements Command {
    private GameScene scene;

    public GameActionCommand(GameScene gameScene) {
        this.scene = gameScene;
    }

    public GameScene getScene() {
        return scene;
    }
}
