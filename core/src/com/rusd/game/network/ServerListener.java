package com.rusd.game.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.entity.Entity;
import com.rusd.game.server.ServerWorld;

/**
 * Created by shane on 7/3/15.
 */
public class ServerListener extends Listener {
    public static final String tag = ServerListener.class.getSimpleName();

    ServerWorld serverWorld;
    Server server;

    public ServerListener(ServerWorld serverWorld, Server server) {
        this.serverWorld = serverWorld;
        this.server = server;
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
            connection.sendTCP(login);

        }
    }

    @Override
    public void connected(Connection connection) {
        //   serverWorld.connectPlayer(connection);
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        serverWorld.disconnectPlayer(connection);
        Log.info(tag, "Player lost connection: " + connection);
        connection.close();
        for (Connection c : server.getConnections()) {
            Log.info(tag, c.toString());

        }


    }
}
