package com.rusd.game.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.network.TransitWorld;

/**
 * Created by shane on 7/5/15.
 */
public class TransitWorldRunnable implements Runnable {
    public static final String tag = TransitWorldRunnable.class.getSimpleName();

    TransitWorld tw;
    Connection c;

    public TransitWorldRunnable(TransitWorld tw, Connection connection) {
        this.tw = tw;
        this.c = connection;
    }

    @Override
    public void run() {
        Log.info(tag, Thread.currentThread().toString() + " ran");
        //c.sendUDP(tw);
    }

}
