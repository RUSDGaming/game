package com.rusd.game.client;

import java.util.ArrayList;

/**
 * Created by shane on 7/4/15.
 */
public class ClientWorld {
    ArrayList<ClientEntity> entities = new ArrayList<>();


    public ArrayList<ClientEntity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<ClientEntity> entities) {
        this.entities = entities;
    }
}
