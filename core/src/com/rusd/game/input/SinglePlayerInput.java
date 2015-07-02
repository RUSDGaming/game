package com.rusd.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputProcessorQueue;
import com.rusd.game.screens.MainGameScreen;

/**
 * Created by shane on 7/2/15.
 */
public class SinglePlayerInput implements InputProcessor {

    private MainGameScreen game;

    public SinglePlayerInput(MainGameScreen game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
