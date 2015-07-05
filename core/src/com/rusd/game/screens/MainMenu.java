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
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rusd.game.constants.Constants;
import com.rusd.game.network.Login;
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
    private Thread connection;
    private Client client;

    private Label ipLabel;
    private TextField ipText;
    private Label nameLabel;
    private TextField nameText;

    private Label connectionStatus;
    private TextButton joinButton;

    public MainMenu(MainGameClass game) {
        Gdx.app.log(tag, Thread.currentThread().toString());

        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        ipLabel = new Label("IP ADDRESS: ", skin);
        ipText = new TextField("localhost", skin);
        nameLabel = new Label("User Name: ", skin);
        nameText = new TextField("", skin);

        connectionStatus = new Label("Status", skin);
        joinButton = new TextButton("join", skin);

        client = new Client();

        RegisterClasses.register(client);

        client.addListener(loginResponseListener);

        connection = new Thread(client);
        connection.start();


        joinButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tryConnection();
                return true;
            }

            ;
        });


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
//                    tryConnection();
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

        table.add(connectionStatus).expandX().colspan(2);
        table.row();
        table.add(ipLabel).expandX().right().top();
        table.add(ipText).expandX().left().top();
        table.row();
        table.add(nameLabel).expandX().right().top();
        table.add(nameText).expandX().left().top();
        table.row();
        table.add(joinButton).expandX().colspan(2);

        table.row().padBottom(Constants.HEIGHT / 2);
        table.add(exitButton).expandX().colspan(2);
//        table.debug();


    }

    public boolean tryConnection() {



        try {
            if (!client.isConnected()) {
                client.connect(5000, ipText.getText(), 54555, 54777);
            }
            if (client.isConnected()) {
                Gdx.app.log(tag, "connection successFull");
                connectionStatus.setText("Connection Success");

                Login login = new Login();
                login.setUsername(nameText.getName());
                client.sendUDP(login);
//                client.sendTCP(login);

                return client.isConnected();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public Listener loginResponseListener = new Listener() {
        @Override
        public void received(Connection connection, Object o) {
            if (o instanceof Login) {
                Login login = (Login) o;
                if (login.getSuccess()) {
                    Gdx.app.postRunnable(changeScreen);

                } else {
                    connectionStatus.setText(login.getLoginResponse());
                }


            }

        }
    };

    // when you change the screen from anonther thread you need to use this runable.
    public Runnable changeScreen = new Runnable() {
        @Override
        public void run() {
            game.setScreen(new MultiPlayerGameScreen(game, client));
        }
    };


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
