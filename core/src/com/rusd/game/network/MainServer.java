package com.rusd.game.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

/**
 * Created by shane on 7/3/15.
 */
public class MainServer {
    public static final String tag = MainServer.class.getSimpleName();


    public static void main(String[] arrrgs) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        Log.info(tag, "Starting Server...");
        Server server;
        server = new Server();
        server.start();
        server.bind(54555, 54777);
        RegisterClasses.register(server);
        server.addListener(new ServerListener());
        Log.info(tag, "Server Started successfully!");
    }


}
