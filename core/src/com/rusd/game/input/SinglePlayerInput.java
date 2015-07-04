package com.rusd.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.StatsComponent;
import com.rusd.game.world.MainWorld;

/**
 * Created by shane on 7/2/15.
 */
public class SinglePlayerInput implements InputProcessor {
    public static final String tag = InputProcessor.class.getSimpleName();

    private Float horizontalAxis = 0f;
    private Float verticalAxis = 0f;

    public void applyInputToWorld(MainWorld world){

        for (Entity e : world.players) {
            if (e == null) {
                continue;
            }

            Body playerBody = e.bodyComponent;
            StatsComponent playerStats = e.statsComponent;
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().scl(.9f));
            playerBody.applyLinearImpulse(
                    horizontalAxis * playerStats.getAcceleration(),
                    verticalAxis * playerStats.getAcceleration(),
                    playerBody.getPosition().x,
                    playerBody.getPosition().y,
                    true);


            //prevents the awkward sliding at low speeds after damping
            Float x = playerBody.getLinearVelocity().x;
            Float y = playerBody.getLinearVelocity().y;

            if (Math.abs(x) < 1f) {
                x = 0f;
            }
            if (Math.abs(y) < 1f) {
                y = 0f;
            }

            playerBody.setLinearVelocity(x, y);

        }

//        Vector2 vec = playerBody.getLinearVelocity().clamp(-playerStats.getMaxSpeed(), playerStats.getMaxSpeed());
        //playerBody.setLinearVelocity(vec);
//        Gdx.app.log(tag, playerBody.getLinearVelocity().toString());


    }



    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:{
                horizontalAxis -= 1;
                break;
            }
            case Input.Keys.E:{
                horizontalAxis += 1;
                break;
            }
            case Input.Keys.O:{
                verticalAxis -= 1;
                break;
            }
            case Input.Keys.COMMA:{
                verticalAxis += 1;
                break;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:{
                horizontalAxis += 1;
                break;
            }
            case Input.Keys.E:{
                horizontalAxis -= 1;
                break;
            }
            case Input.Keys.O:{
                verticalAxis += 1;
                break;
            }
            case Input.Keys.COMMA:{
                verticalAxis -= 1;
                break;
            }
        }
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
