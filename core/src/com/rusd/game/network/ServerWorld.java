package com.rusd.game.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.rusd.game.entity.DeathTimerComponent;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.StatsComponent;

import java.util.ArrayList;

/**
 * Created by shane on 7/3/15.
 */
public class ServerWorld {
    public static final String tag = ServerWorld.class.getSimpleName();

    private World boxWorld;


    private ArrayList<Body> bodies = new ArrayList<>();
    private ArrayList<StatsComponent> statsComponents = new ArrayList<>();
    private ArrayList<DeathTimerComponent> deathTimerComponents = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();

    public ServerWorld() {
        Box2D.init();
        boxWorld = new World(new Vector2(0, 0), true);

    }


    public void stepWorld() {
        boxWorld.step(1 / 60f, 6, 2);
    }


}
