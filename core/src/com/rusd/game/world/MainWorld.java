package com.rusd.game.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by shane on 7/2/15.
 */
public class MainWorld {

    World boxWorld;
    Box2DDebugRenderer renderer;
    public MainWorld(){
        Box2D.init();
        boxWorld = new World(new Vector2(0,0),true);
        renderer = new Box2DDebugRenderer();

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
        bodyDef.position.set(100, 300);
        Body body = boxWorld.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
        return body;
    }

}
