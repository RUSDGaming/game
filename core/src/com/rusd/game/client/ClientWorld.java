package com.rusd.game.client;

import com.rusd.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by shane on 7/4/15.
 */
public class ClientWorld {

    private ArrayList<Entity> entities = new ArrayList<>();

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
