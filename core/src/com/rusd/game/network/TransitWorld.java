package com.rusd.game.network;

import com.rusd.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by shane on 7/3/15.
 */
public class TransitWorld {

    private ArrayList<Entity> entities;

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
