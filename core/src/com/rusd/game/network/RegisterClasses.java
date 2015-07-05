package com.rusd.game.network;

import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.rusd.game.entity.DeathTimerComponent;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.RenderComponent;
import com.rusd.game.entity.StatsComponent;

import java.util.ArrayList;

/**
 * Created by shane on 7/3/15.
 */
public class RegisterClasses {
    public static void register(EndPoint endPoint) {

        Kryo kryo = endPoint.getKryo();
        kryo.register(ClientInput.class);
        kryo.register(ArrayList.class);
        kryo.register(Entity.class);
        kryo.register(TransitWorld.class);
        kryo.register(Login.class);
        kryo.register(DeathTimerComponent.class);
        kryo.register(RenderComponent.class);
        kryo.register(StatsComponent.class);
        kryo.register(Body.class);
    }
}
