package com.rusd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import com.rusd.game.constants.Constants;
import com.rusd.game.input.MultiPlayerInput;
import com.rusd.game.network.ClientInput;
import com.rusd.game.network.RegisterClasses;
import com.rusd.game.start.MainGameClass;

import java.io.IOException;

/**
 * Created by shane on 7/3/15.
 */
public class MultiPlayerGameScreen implements Screen {

    public static final String tag = MainGameScreen.class.getSimpleName();
    public OrthographicCamera camera;
    private MultiPlayerInput input;
    public final MainGameClass game;
    private Thread connection;
    private Client client;

    public MultiPlayerGameScreen(MainGameClass game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.VIEWPORTWIDTH, Constants.VIEWPORTHEIGHT);
        Gdx.input.setInputProcessor(input);
        client = new Client();
        RegisterClasses.register(client);


        connection = new Thread(client);
        connection.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        inputHandler();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();


        game.batch.end();

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


    public void inputHandler() {
        ClientInput clientInput = new ClientInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.log(tag, "closing game");
            Gdx.app.exit();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            clientInput.setFire1(true);
            client.sendUDP(clientInput);
        }


    }
}
