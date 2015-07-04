package com.rusd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.esotericsoftware.kryonet.Client;
import com.rusd.game.constants.Constants;
import com.rusd.game.network.RegisterClasses;
import com.rusd.game.start.MainGameClass;

import java.io.IOException;

/**
 * Created by shane on 7/3/15.
 */
public class MainMenu implements Screen {

    public static final String tag = MainMenu.class.getSimpleName();

    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private MainGameClass game;


    private Label connectionStatus;

    public MainMenu(MainGameClass game) {

        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label ipLabel = new Label("IP ADDRESS: ", skin);
        TextField ipText = new TextField("localhost", skin);
        connectionStatus = new Label("Status", skin);


        // table.setTouchable(Touchable.disabled);

        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.bottom();

        TextButton button2 = new TextButton("Button 2", skin);
        TextButton exitButton = new TextButton("Exit game ", skin);

        ipText.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    Gdx.app.log(tag, "Enter pressed on text: " + ipText.getText());
                    tryConnection(ipText.getText());
                }
                return super.keyDown(event, keycode);
            }
        });


        exitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(tag, "exting game");
                Gdx.app.exit();
                return true;
            }
        });

        button2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log(tag, "reset Screen");

                return false;
            }
        });

        table.add(ipLabel).expandX().right().top();
        table.add(ipText).expandX().left().top();
        table.row();
        table.add(connectionStatus).expandX().colspan(2);

        table.row().padBottom(Constants.HEIGHT / 2);
        table.add(exitButton).expandX().colspan(2);
//        table.debug();


    }

    public boolean tryConnection(String ip) {
        Thread connection;
        Client client = new Client();
        RegisterClasses.register(client);

        connection = new Thread(client);
        connection.start();

        try {
            client.connect(5000, ip, 54555, 54777);
            if (client.isConnected()) {
                Gdx.app.log(tag, "connection successFull");
                connectionStatus.setText("Connection Success");
                return client.isConnected();
            }
        } catch (IOException e) {
            Gdx.app.log(tag, "Invalid name or something");
            connectionStatus.setText("Invalid name or something");

        }

        connectionStatus.setText("connection failed");
        Gdx.app.log(tag, "connection failed");
        return false;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));


        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
        stage.dispose();
        skin.dispose();

    }
}
