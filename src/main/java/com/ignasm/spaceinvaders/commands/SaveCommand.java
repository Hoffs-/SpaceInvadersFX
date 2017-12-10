package com.ignasm.spaceinvaders.commands;

import com.ignasm.spaceinvaders.GameScene;
import com.ignasm.spaceinvaders.SceneMemento;

public class SaveCommand extends GameActionCommand {
    public SaveCommand(GameScene gameScene) {
        super(gameScene);
    }

    @Override
    public void execute() {
        SceneMemento memento = getScene().getMemento();
        getScene().setLastSave(memento);
    }
}
