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
            Gdx.app.log(tag, "got something");
            //TransitWorld tw = (TransitWorld) o;
            //clientWorld.setEntities(tw.getEntities());

        }
    }

    @Override
    public void disconnected(Connection connection) {
        Gdx.app.log(tag, "Was dissconnected... " + connection.toString());
        super.disconnected(connection);
    }
}
