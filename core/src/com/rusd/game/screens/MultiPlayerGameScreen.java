package com.rusd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryonet.Client;
import com.rusd.game.client.ClientEntity;
import com.rusd.game.client.ClientWorld;
import com.rusd.game.constants.Constants;
import com.rusd.game.input.MultiPlayerInput;
import com.rusd.game.network.ClientInput;
import com.rusd.game.network.ClientListener;
import com.rusd.game.start.MainGameClass;

import java.util.function.Consumer;

/**
 * Created by shane on 7/3/15.
 */
public class MultiPlayerGameScreen implements Screen {

    public static final String tag = MultiPlayerGameScreen.class.getSimpleName();
    public OrthographicCamera camera;
    private MultiPlayerInput input;
    public final MainGameClass game;
    private ShapeRenderer sr;

    private Client client;
    private ClientWorld clientWorld;


    public MultiPlayerGameScreen(MainGameClass game, Client client) {
        Gdx.app.log(tag, Thread.currentThread().toString());

        this.game = game;
        clientWorld = new ClientWorld();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.VIEWPORTWIDTH, Constants.VIEWPORTHEIGHT);
        input = new MultiPlayerInput();
        Gdx.input.setInputProcessor(input);
        this.client = client;

        client.addListener(new ClientListener(clientWorld));

        sr = new ShapeRenderer();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        //client.sendUDP(new EntityRequest());
        inputHandler();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();


        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(camera.combined);
        sr.setColor(Color.BLUE);
        clientWorld.getEntities().stream().forEach(entityRenderer);
        sr.end();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.end();

    }

    Consumer<ClientEntity> entityRenderer = (ClientEntity e) -> {

        sr.circle(e.getX(), e.getY(), e.getRadius());
    };

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
