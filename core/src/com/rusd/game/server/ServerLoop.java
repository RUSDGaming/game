package com.rusd.game.server;

import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Connection;
import com.rusd.game.network.TransitWorld;

import java.util.function.Consumer;

/**
 * Created by shane on 7/4/15.
 */
public class ServerLoop implements Runnable {

    private ServerWorld world;

    private Long timeStep = 20L;
    private Long start;
    private TransitWorld transitWorld;

    public ServerLoop(ServerWorld world) {
        this.world = world;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                start = TimeUtils.millis();
                world.stepWorld();
                transitWorld = new TransitWorld();
                transitWorld.setEntities(world.getEntities());
                world.getPlayers().keySet().stream().forEach(connectionConsumer);

                Thread.sleep(timeStep + start - TimeUtils.millis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    Consumer<Connection> connectionConsumer = (Connection c) -> {
        c.sendUDP(transitWorld);
    };


}
