package com.rusd.game.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.network.RegisterClasses;
import com.rusd.game.network.ServerListener;

import java.io.IOException;

/**
 * Created by shane on 7/3/15.
 */
public class MainServer {
    public static final String tag = MainServer.class.getSimpleName();
    public ServerWorld serverWorld;


    public MainServer() {
        serverWorld = new ServerWorld();
    }

    public static void main(String[] arrrgs) throws IOException {
        Log.set(Log.LEVEL_INFO);


        Log.info(tag, "Starting Server...");
        MainServer mainServer = new MainServer();


        Server server;
        server = new Server();

        Thread serverThread = new Thread(server);
        serverThread.start();
        RegisterClasses.register(server);
        server.bind(54555, 54777);
        server.addListener(new ServerListener(mainServer.serverWorld, server));


        Thread loopThread = new Thread(new ServerLoop(mainServer.serverWorld, server));
        loopThread.start();


        Log.info(tag, "Server Started successfully!");
    }


}
