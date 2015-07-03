package com.rusd.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.rusd.game.entity.DeathTimerComponent;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.StatsComponent;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by shane on 7/2/15.
 */
public class MainWorld {
    public static final String tag = MainWorld.class.getSimpleName();


    private ArrayList<Body> bodies = new ArrayList<>();
    private ArrayList<StatsComponent> statsComponents = new ArrayList<>();
    private ArrayList<DeathTimerComponent> deathTimerComponents = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
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
        statsComponent.setAcceleration(12f);
        statsComponent.setEntity(player);
        player.statsComponent = statsComponent;
        statsComponents.add(statsComponent);
    }

    public void stepWorld(){
        boxWorld.step(1 / 60f, 6, 2);
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

    /**
     * for now this just spawns a bullet at the player and moves it toward the cursor
     *
     * @param cam
     * @return
     */
    public Body spawnPlayerBullet(Camera cam) {

        if (player.statsComponent.getLastAttack() + player.statsComponent.getReloadTime() > TimeUtils.millis()) {
            return null;
        }
        player.statsComponent.setLastAttack(TimeUtils.millis());
        Gdx.app.debug(tag, "Shots fired");


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        bodyDef.position.set(player.bodyComponent.getPosition().x, player.bodyComponent.getPosition().y);
        bodyDef.fixedRotation = true;

        Body body = boxWorld.createBody(bodyDef);
        body.setBullet(true);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.33f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();

        Entity bullet = new Entity();

        bullet.setBodyComponent(body);

        StatsComponent statsComponent = new StatsComponent();
        statsComponent.setEntity(bullet);
        statsComponent.setHealth(10);

        DeathTimerComponent deathTimerComponent = new DeathTimerComponent(2000L, bullet);
        bullet.setStatsComponent(statsComponent);
        deathTimerComponents.add(deathTimerComponent);
        bodies.add(body);
        entities.add(bullet);
        statsComponents.add(statsComponent);

        moveBodyTowardCursor(body,cam);
        return body;

    }

    public void moveBodyTowardCursor(Body body, Camera cam) {

        Float x = Float.valueOf(Gdx.input.getX());
        Float y = Float.valueOf(Gdx.input.getY());

        Vector3 cords = new Vector3(x, y, 0);
        // set mouse cords to the inworld position
        cam.unproject(cords);

        Vector3 diff = cords.sub(body.getPosition().x, body.getPosition().y, 0).nor();


        body.setLinearVelocity(diff.x * 100, diff.y * 100);
    }


    public void cleanUp() {

        // should problably be using flat arrays for performance, I'm not concerned at this point though
        Stream stream = deathTimerComponents.stream().filter(dtc -> !dtc.isRunning());
        ArrayList<DeathTimerComponent> deathTimerComponentsCopy = new ArrayList<>();
        stream.forEach(dtc -> deathTimerComponentsCopy.add((DeathTimerComponent) dtc));
        deathTimerComponents = deathTimerComponentsCopy;

        ArrayList<Entity> entitiesCopy = new ArrayList<>();
        ArrayList<StatsComponent> statsComponentsCopy = new ArrayList<>();

        // destroy all the bodies if it's entiy health is 0
        statsComponents.stream().filter(sc -> sc.getHealth() <= 0).forEach(statConsumer);
        statsComponents.stream().filter(sc -> sc.getHealth() > 0).forEach(sc -> statsComponentsCopy.add(sc));
        statsComponents = statsComponentsCopy;


    }

    // What is how can java 8?
    //http://briancovelli.com/wp-content/uploads/2014/05/i-have-no-idea-what-im-doing-science-dog.jpg
    // destroys the entitys that get passed here.
    Consumer<StatsComponent> statConsumer = (StatsComponent sc) -> {
        boxWorld.destroyBody(sc.getEntity().getBodyComponent());
        entities.remove(sc.getEntity());

    };

    public Void destroyBody(StatsComponent sc) {
        boxWorld.destroyBody(sc.getEntity().getBodyComponent());
        Gdx.app.log(tag, "Body Destroyed Mother Fucker");
        return null;
    }


}
