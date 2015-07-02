package com.rusd.game.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.StatsComponent;

import java.util.ArrayList;

/**
 * Created by shane on 7/2/15.
 */
public class MainWorld {



    private ArrayList<Body> bodies = new ArrayList<>();
    public ArrayList<StatsComponent> statsComponents = new ArrayList<>();
    public Entity player = new Entity();
    private World boxWorld;
    private Box2DDebugRenderer renderer;

    public MainWorld(){
        Box2D.init();
        boxWorld = new World(new Vector2(0,0),true);
        renderer = new Box2DDebugRenderer();
        initPlayer();

    }

    public void initPlayer(){
        player.bodyComponent = addBody();
        StatsComponent statsComponent = new StatsComponent();
        statsComponent.setHealth(10);
        statsComponent.setMaxSpeed(70f);
        statsComponent.setArmor(1);
        statsComponent.setAcceleration(15f);
        statsComponent.setEntity(player);
        player.statsComponent = statsComponent;
        statsComponents.add(statsComponent);
    }

    public void stepWorld(){
        boxWorld.step(1/60f,6,2);
    }

    public void renderWorld(Camera cam){
        renderer.render(boxWorld, cam.combined);
    }

    /**
     * Create a circle body on the screen
     */
    public Body addBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(10, 10);
        bodyDef.fixedRotation = true;
        Body body = boxWorld.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
        bodies.add(body);
        return body;
    }

}
