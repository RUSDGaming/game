package com.rusd.game.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.entity.Entity;
import com.rusd.game.server.ServerWorld;

/**
 * Created by shane on 7/3/15.
 */
public class ServerListener extends Listener {
    public static final String tag = ServerListener.class.getSimpleName();

    ServerWorld serverWorld;

    public ServerListener(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }

    @Override
    public void received(Connection connection, Object o) {
//        super.received(connection, o);
        if (o instanceof ClientInput) {
            ClientInput clientInput = (ClientInput) o;
            if (clientInput.getFire1()) {
                Entity player = serverWorld.getPlayerByConnection(connection);
                if (player != null) {
                    Vector2 mousePos = new Vector2(800, 450);
                    serverWorld.addBullet(player, mousePos);
                    Log.info(tag, "Le shots fired");
                } else {
                    Log.info(tag, "player was null: " + player);
                }

            }
        } else if (o instanceof Login) {
            Login login = (Login) o;
            if (serverWorld.connectPlayer(connection, login)) {
                login.setSuccess(true);
                login.setLoginResponse("Login Successfull");
            }
            connection.sendUDP(login);



        }
    }

    @Override
    public void connected(Connection connection) {
        //   serverWorld.connectPlayer(connection);
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
//        serverWorld.dissconnectPlayer(connection);
        Log.info(tag, "Player is lost connection: " + connection);
        super.disconnected(connection);
    }
}
