package com.rusd.game.network;

import com.esotericsoftware.minlog.Log;
import com.rusd.game.client.ClientEntity;
import com.rusd.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by shane on 7/3/15.
 */
public class TransitWorld {

    public TransitWorld() {

    }


    private ArrayList<ClientEntity> entities = new ArrayList<>();

    public ArrayList<ClientEntity> getClientEntities() {
        return entities;
    }

    public void setClientEntities(ArrayList<ClientEntity> entities) {
        this.entities = entities;
    }


    public void setEntities(ArrayList<Entity> entities) {
        this.entities = new ArrayList<>();
        for (Entity e : entities) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setPosition(e.getBodyComponent().getPosition());
            clientEntity.setTextureId(-1);
            try {
                clientEntity.setRadius(e.getBodyComponent().getFixtureList().get(0).getShape().getRadius());

            } catch (Exception ex) {
                Log.info(ex.toString());
            }
            this.entities.add(clientEntity);
        }


    }

}
