package com.rusd.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by shane on 7/3/15.
 */
public class ServerListener extends Listener {
    public static final String tag = ServerListener.class.getSimpleName();

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if (o instanceof ClientInput) {
            ClientInput clientInput = (ClientInput) o;
            if (clientInput.getFire1()) {
                Log.info(tag, "Le shots fired");
            }
        }
    }
}
