package com.rusd.game.server;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import com.rusd.game.entity.*;
import com.rusd.game.network.ClientInput;
import com.rusd.game.network.Login;
import com.rusd.game.observers.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
    private Map<Entity, ClientInput> clientInputMap = new HashMap<>();

    private ArrayList<Entity> entiesToRemove = new ArrayList<>();

    private ArrayList<ScoreComponent> scores = new ArrayList<>();
    public Boolean resendScore = false;

    public ArrayList<ScoreComponent> getScores() {
        return scores;
    }

    public Subject subject;

    public ServerWorld() {
        Box2D.init();
        boxWorld = new World(new Vector2(0, 0), true);
        boxWorld.setContactListener(new ContactListenerImpl());

    }

    public void stepWorld() {
        boxWorld.step(1 / 60f, 6, 2);
        cleanUp();
        clientInputMap.forEach(inputConsumer);
    }

    BiConsumer<Entity, ClientInput> inputConsumer = (entity, clientInput) -> {

        if (clientInput.getFire1()) {
            addBullet(entity, clientInput.getMouseWorldPos());
        }

        Float verticalAxis = 0f;
        Float horizontalAxis = 0f;

        if (clientInput.getRight())
            horizontalAxis += 1f;
        if (clientInput.getLeft())
            horizontalAxis -= 1f;
        if (clientInput.getUp())
            verticalAxis += 1f;
        if (clientInput.getDown())
            verticalAxis -= 1f;


        Body playerBody = entity.bodyComponent;
        StatsComponent playerStats = entity.statsComponent;
        playerBody.setLinearVelocity(playerBody.getLinearVelocity().scl(.9f));
        playerBody.applyLinearImpulse(
                horizontalAxis * playerStats.getAcceleration(),
                verticalAxis * playerStats.getAcceleration(),
                playerBody.getPosition().x,
                playerBody.getPosition().y,
                true);

        //prevents the awkward sliding at low speeds after damping
        Float x = playerBody.getLinearVelocity().x;
        Float y = playerBody.getLinearVelocity().y;

        if (Math.abs(x) < 1f) {
            x = 0f;
        }
        if (Math.abs(y) < 1f) {
            y = 0f;
        }

        playerBody.setLinearVelocity(x, y);

    };

    public void resetPlayer(Entity player, Float xPos, Float yPos) {
        Log.info(tag, "resetting player");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(xPos, yPos);
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

        body.setUserData(player);
        player.setDestroyMe(false);

        bodies.add(body);

    }


    public Entity createPlayer() {

        Entity player = new Entity(subject);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(10, 10);
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
        statsComponent.setHealth(10f);
        statsComponent.setReloadTime(100L);
        statsComponent.setAcceleration(15f);
        statsComponent.setMaxSpeed(50f);
        player.setStatsComponent(statsComponent);

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.setEntity(player);
        Circle circle = new Circle();
        circle.setPosition(body.getPosition().x, body.getPosition().y);
        renderComponent.setShape(RenderComponent.Shape.CIRCLE);
        renderComponent.setCircle(circle);
        player.setRenderComponent(renderComponent);

        body.setUserData(player);
        player.setParentEntity(player);
        player.setEntityType(Entity.EntityType.PLAYER);
        ScoreComponent scoreComponent = new ScoreComponent(player);
        player.setScoreComponent(scoreComponent);

        scores.add(scoreComponent);
        bodies.add(body);
        entities.add(player);


        return player;
    }


    public void addBullet(Entity player, Vector3 mousePos) {

        if (player.statsComponent.getLastAttack() + player.statsComponent.getReloadTime() > TimeUtils.millis()) {
            return;
        }

        player.statsComponent.setLastAttack(TimeUtils.millis());

        //Log.info(tag, "Shots Fired");

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(player.bodyComponent.getPosition().x, player.bodyComponent.getPosition().y);
        bodyDef.fixedRotation = true;

        Body body = boxWorld.createBody(bodyDef);
        body.setBullet(true);


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);

        circleShape.dispose();

        Entity bullet = new Entity(subject);
        bullet.setColor(player.getColor());
        bullet.setParentEntity(player);
        body.setUserData(bullet);
        bullet.setBodyComponent(body);

        StatsComponent statsComponent = new StatsComponent();
        statsComponent.setEntity(bullet);
        statsComponent.setHealth(10f);


        DeathTimerComponent deathTimerComponent = new DeathTimerComponent(2000L, bullet);
        bullet.setStatsComponent(statsComponent);
        bullet.setEntityType(Entity.EntityType.BULLET);


        deathTimerComponents.add(deathTimerComponent);
        bodies.add(body);
        entities.add(bullet);


        //Vector3 cords = new Vector3(mousePos.x, mousePos.y, 0);


        //Vector3 diff = cords.sub(body.getPosition().x, body.getPosition().y, 0).nor();


        Vector3 diff = mousePos.sub(body.getPosition().x, body.getPosition().y, 0).nor();

        body.setLinearVelocity(diff.x * 100, diff.y * 100);



    }

    public Entity getPlayerByConnection(Connection c) {
        return players.get(c);
    }

    public boolean connectPlayer(Connection c, Login login) {
        Log.info(tag, "Connection id is: " + c.getID());
        Log.info(tag, "Connection Info, TCP: " + c.getRemoteAddressTCP() + " UDP: " + c.getRemoteAddressUDP());

        Entity player = createPlayer();
        player.setName(login.getUsername());
        Log.info(tag, login.getUsername() + " Logged in");
        player.setColor(new Color(MathUtils.random(0f, 1f), MathUtils.random(0f, 1f), MathUtils.random(0f, 1f), 1));
        player.setName(login.getUsername());

        if (players.containsKey(c)) {
            Log.info(tag, "player already logged in" + login.getUsername());
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
            e.disconnect = true;
            e.setDestroyMe(true);
            return true;
        }
        connection.close();

        return false;
    }


    public void cleanUp() {

        // should problably be using flat arrays for performance, I'm not concerned at this point though
        Stream stream = deathTimerComponents.stream().filter(dtc -> !dtc.isRunning());
        ArrayList<DeathTimerComponent> deathTimerComponentsCopy = new ArrayList<>();
        stream.forEach(dtc -> deathTimerComponentsCopy.add((DeathTimerComponent) dtc));
        deathTimerComponents = deathTimerComponentsCopy;

        ArrayList<Entity> entitiesCopy = new ArrayList<>();
        ArrayList<StatsComponent> statsComponentsCopy = new ArrayList<>();

        entiesToRemove = new ArrayList<>();
        entities.stream().forEach(markForDestruction);
        entiesToRemove.stream().forEach(removeConsumer);
        entiesToRemove = new ArrayList<>();

    }

    Consumer<Entity> markForDestruction = (Entity entity) -> {
        if (!entity.getDestroyMe()) {
            return;
        }

        switch (entity.getEntityType()) {
            case PLAYER: {
                if (entity.disconnect) {
                    entiesToRemove.add(entity);
                    break;
                }
                bodies.remove(entity.getBodyComponent());
                boxWorld.destroyBody(entity.getBodyComponent());
                entity.getStatsComponent().setHealth(10f);
                resetPlayer(entity, 50f, 50f);
                break;
            }
            case BULLET: {
                entiesToRemove.add(entity);
                break;
            }
            case BUILDING:
                break;
        }

    };

    Consumer<Entity> removeConsumer = (Entity entity) -> {

        statsComponents.remove(entity);
        scores.remove(entity.getScoreComponent());
        clientInputMap.remove(entity);
        bodies.remove(entity.getBodyComponent());
        boxWorld.destroyBody(entity.getBodyComponent());
        entities.remove(entity);
    };

    Consumer<StatsComponent> statConsumer = (StatsComponent sc) -> {
        boxWorld.destroyBody(sc.getEntity().getBodyComponent());
        entities.remove(sc.getEntity());

    };

    public void updateClientInput(Entity e, ClientInput clientInput) {
        clientInputMap.put(e, clientInput);
    }
}
