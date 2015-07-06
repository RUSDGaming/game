package com.rusd.game.server;

import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.StatsComponent;
import com.rusd.game.network.TransitWorld;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by shane on 7/4/15.
 */
public class ServerLoop implements Runnable {
    public static final String tag = ServerLoop.class.getSimpleName();

    private ServerWorld world;
    private ExecutorService executorService;

    private Long timeStep = 20L;
    private Long start;
    private TransitWorld transitWorld;
    private Server server;

    public ServerLoop(ServerWorld world, Server server) {
        this.server = server;
        executorService = Executors.newFixedThreadPool(4);
        this.world = world;
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                start = TimeUtils.millis();
                world.stepWorld();
                transitWorld = new TransitWorld();
                Entity e = new Entity();
                //e.setRenderComponent(new RenderComponent());
                e.setStatsComponent(new StatsComponent());


                transitWorld.setEntities(world.getEntities());
//                if (transitWorld.getClientEntities() != null){
//                    Log.info(tag, "sending entities: " + transitWorld.getClientEntities().size());
//                }
//                Login l = new Login();
//                l.setSuccess(true);
//                l.setLoginResponse("game loope...");

//                server.sendToAllUDP(world.getEntities());
//                world.getPlayers().keySet().stream().forEach(connectionConsumer);
                server.sendToAllUDP(transitWorld);


                Thread.sleep(timeStep + start - TimeUtils.millis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    Consumer<Connection> connectionConsumer = (Connection c) -> {
        //server.sendToAllUDP(transitWorld);
//        Login l = new Login();
//        l.setSuccess(true);
//        l.setLoginResponse("game loope...");
//        c.sendUDP(c);
        server.sendToAllUDP(transitWorld);
        //c.sendUDP(transitWorld);
        Log.info(tag, "sending info");
    };


}
