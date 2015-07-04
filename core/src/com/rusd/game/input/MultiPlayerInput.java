package com.rusd.game.input;

import com.badlogic.gdx.InputProcessor;
import com.rusd.game.network.ClientInput;

/**
 * Created by shane on 7/3/15.
 */
public class MultiPlayerInput implements InputProcessor {


    ClientInput clientInput = new ClientInput();


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

        clientInput.setFire1(true);


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        clientInput.setFire1(false);
        return true;
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
