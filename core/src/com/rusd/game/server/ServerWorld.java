package com.rusd.game.server;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.entity.DeathTimerComponent;
import com.rusd.game.entity.Entity;
import com.rusd.game.entity.RenderComponent;
import com.rusd.game.entity.StatsComponent;
import com.rusd.game.network.Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Connection, Entity> players = new HashMap<>();


    public ServerWorld() {
        Box2D.init();
        boxWorld = new World(new Vector2(0, 0), true);
    }


    public void stepWorld() {
        boxWorld.step(1 / 60f, 6, 2);
    }


    public Entity createPlayer() {

        Entity player = new Entity();


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(100, 100);
        bodyDef.fixedRotation = true;

        Body body = boxWorld.createBody(bodyDef);
        body.setBullet(true);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();


        player.setBodyComponent(body);

        StatsComponent statsComponent = new StatsComponent();
        statsComponent.setEntity(player);
        statsComponent.setHealth(10);
        player.setStatsComponent(statsComponent);

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.setEntity(player);
        Circle circle = new Circle();
        circle.setPosition(body.getPosition().x, body.getPosition().y);
        renderComponent.setShape(RenderComponent.Shape.CIRCLE);
        renderComponent.setCircle(circle);
        player.setRenderComponent(renderComponent);


        bodies.add(body);
        entities.add(player);
        statsComponents.add(statsComponent);

        return player;
    }


    public void addBullet(Entity player, Vector2 mousePos) {

        if (player.statsComponent.getLastAttack() + player.statsComponent.getReloadTime() > TimeUtils.millis()) {
            return;
        }

        player.statsComponent.setLastAttack(TimeUtils.millis());

        Log.debug(tag, "Shots Fired");

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

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.setEntity(bullet);
        Circle circle = new Circle();
        circle.setPosition(body.getPosition().x, body.getPosition().y);
        renderComponent.setShape(RenderComponent.Shape.CIRCLE);
        renderComponent.setCircle(circle);
        bullet.setRenderComponent(renderComponent);


        deathTimerComponents.add(deathTimerComponent);
        bodies.add(body);
        entities.add(bullet);
        statsComponents.add(statsComponent);


        //Vector3 cords = new Vector3(mousePos.x, mousePos.y, 0);


        //Vector3 diff = cords.sub(body.getPosition().x, body.getPosition().y, 0).nor();

        body.setLinearVelocity(100, 100);


    }

    public Entity getPlayerByConnection(Connection c) {
        return players.get(c);
    }

    public boolean connectPlayer(Connection c, Login login) {
        Log.debug(tag, "Connection id is: " + c.getID());
        Log.debug(tag, "Connection Info, TCP: " + c.getRemoteAddressTCP() + " UDP: " + c.getRemoteAddressUDP());

        Entity player = createPlayer();

        if (players.containsKey(c)) {
            Log.debug(tag, "player already logged in" + login.getUsername());
            return true;
        }

        players.put(c, player);

        return true;

    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Map<Connection, Entity> getPlayers() {
        return players;
    }

    public boolean disconnectPlayer(Connection connection) {
        Entity e = players.remove(connection);
        if (e != null) {
            return true;
        }
        return false;
    }
}
