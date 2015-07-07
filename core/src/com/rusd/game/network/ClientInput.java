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
    private Boolean up = false;
    private Boolean down = false;
    private Boolean right = false;
    private Boolean left = false;

    public Boolean getUp() {
        return up;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public Boolean getDown() {
        return down;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public Boolean getLeft() {
        return left;
    }

    public void setLeft(Boolean left) {
        this.left = left;
    }

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
