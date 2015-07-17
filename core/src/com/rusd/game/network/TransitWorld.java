package com.rusd.game.network;

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
            clientEntity.setName(e.getName());
            clientEntity.setColor(e.getColor());
            clientEntity.setTextureId(-1);

            clientEntity.setPosition(e.getBodyComponent().getPosition());
            switch (e.getEntityType()) {

                case PLAYER: {
                    clientEntity.setRadius(e.getBodyComponent().getFixtureList().get(0).getShape().getRadius());
                    break;
                }

                case BULLET: {
                    clientEntity.setRadius(e.getBodyComponent().getFixtureList().get(0).getShape().getRadius());
                    break;
                }
                case BUILDING:
                    clientEntity.setShape(1);
                    clientEntity.setWidth(e.getRenderComponent().getWidth());
                    clientEntity.setHeight(e.getRenderComponent().getHeight());
                    break;
            }


            this.entities.add(clientEntity);
        }


    }

}
