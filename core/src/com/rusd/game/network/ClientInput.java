package com.rusd.game.network;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by shane on 7/3/15.
 */
public class ClientInput {

    public ClientInput() {
    }

    private Boolean fire1 = false;
    private Vector3 mouseWorldPos;

    public Vector3 getMouseWorldPos() {
        return mouseWorldPos;
    }

    public void setMouseWorldPos(Vector3 mouseWorldPos) {
        this.mouseWorldPos = mouseWorldPos;
    }

    public Boolean getFire1() {
        return fire1;
    }

    public void setFire1(Boolean fire1) {
        this.fire1 = fire1;
    }
}
