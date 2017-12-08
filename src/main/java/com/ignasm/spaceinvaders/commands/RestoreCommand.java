package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.GameScene;
import com.ignasm.spaceinvaders.SceneMemento;

public class RestoreCommand extends GameActionCommand {
    public RestoreCommand(GameScene gameScene) {
        super(gameScene);
    }

    @Override
    public void execute() {
        SceneMemento memento = getScene().getLastSave();
        getScene().setMemento(memento);
        getScene().getEnemyShots().clear();
        getScene().getPlayerShots().clear();
    }
}
