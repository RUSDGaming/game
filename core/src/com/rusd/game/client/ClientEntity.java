package com.rusd.game.client;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by shane on 7/5/15.
 */
public class ClientEntity {
    private int textureId;
    private String name = "";

    private float x;
    private float y;
    private float radius;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }


    public void setPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
