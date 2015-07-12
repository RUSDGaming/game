package com.rusd.game.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.network.RegisterClasses;
import com.rusd.game.network.ServerListener;
import com.rusd.game.observers.ScoreBoardObserver;
import com.rusd.game.observers.Subject;

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
        RegisterClasses.register(server);
        Subject subject = new Subject();
        ScoreBoardObserver obvserver = new ScoreBoardObserver();
        mainServer.serverWorld.subject = subject;
        subject.addObserver(obvserver);
        

        Thread serverThread = new Thread(server);
        server.bind(54555, 54777);
        server.addListener(new ServerListener(mainServer.serverWorld, server));

        serverThread.start();

        Thread loopThread = new Thread(new ServerLoop(mainServer.serverWorld, server));
        loopThread.start();


        Log.info(tag, "Server Started successfully!");
    }


}
