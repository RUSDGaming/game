package com.rusd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rusd.game.constants.Constants;
import com.rusd.game.input.SinglePlayerInput;
import com.rusd.game.start.MainGameClass;
import com.rusd.game.world.MainWorld;

/**
 * Created by shane on 6/27/15.
 */
public class MainGameScreen implements Screen {

    public final MainGameClass game;
    public static final String tag = MainGameScreen.class.getSimpleName();
    public OrthographicCamera camera;
    private MainWorld mainWorld;

    public MainGameScreen(MainGameClass game){

        this.game = game;
        mainWorld = new MainWorld();
        mainWorld.addBody();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WIDTH,Constants.HEIGHT);
        Gdx.input.setInputProcessor(new SinglePlayerInput(this));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        inputHandler();
        mainWorld.stepWorld();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        mainWorld.renderWorld(camera);
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();



    }

    public void inputHandler(){

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            System.out.println("Space was pressed");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.log(tag,"closing game");
            Gdx.app.exit();
        }


    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
