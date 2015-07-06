package com.rusd.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.rusd.game.client.ClientEntity;
import com.rusd.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by shane on 7/3/15.
 */
public class RegisterClasses {
    public static final String tag = RegisterClasses.class.getSimpleName();
    public static void register(EndPoint endPoint) {


        Kryo kryo = endPoint.getKryo();
        kryo.register(ClientInput.class);
        kryo.register(ArrayList.class);
        kryo.register(Login.class);
        kryo.register(ClientEntity.class);
        kryo.register(Entity.class);
        kryo.register(com.rusd.game.network.TransitWorld.class);
//        kryo.register(DeathTimerComponent.class);
//        kryo.register(RenderComponent.class);
//        kryo.register(StatsComponent.class);
//        kryo.register(Body.class);
//        kryo.register(Vector2.class);

    }
}
