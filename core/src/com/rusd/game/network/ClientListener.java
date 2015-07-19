package com.rusd.game.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rusd.game.client.ClientWorld;

/**
 * Created by shane on 7/4/15.
 */
public class ClientListener extends Listener {

    public static final String tag = ClientListener.class.getSimpleName();


    ClientWorld clientWorld;

    public ClientListener(ClientWorld clientWorld) {
        this.clientWorld = clientWorld;
    }


    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if (o instanceof TransitWorld) {
            TransitWorld tw = (TransitWorld) o;
            if (tw.getClientEntities() != null) {
                clientWorld.setEntities(tw.getClientEntities());
            }
        }
        if (o instanceof ScoreBoard) {
            //Log.info(tag,"got a scoreBoard");
            ScoreBoard sb = (ScoreBoard) o;
            clientWorld.setScoreBoard(sb);
        }
    }


    @Override
    public void disconnected(Connection connection) {
        Gdx.app.log(tag, "Was disconnected... " + connection.toString());
        Gdx.app.exit();
        //super.disconnected(connection);
    }

    // when you change the screen from anonther thread you need to use this runable.

}
