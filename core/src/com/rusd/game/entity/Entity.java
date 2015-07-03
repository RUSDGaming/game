package com.rusd.game.entity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by shane on 7/2/15.
 */
public class Entity {
    public Body bodyComponent;
    public StatsComponent statsComponent;


    public Body getBodyComponent() {
        return bodyComponent;
    }

    public void setBodyComponent(Body bodyComponent) {
        this.bodyComponent = bodyComponent;
    }

    public StatsComponent getStatsComponent() {
        return statsComponent;
    }

    public void setStatsComponent(StatsComponent statsComponent) {
        this.statsComponent = statsComponent;
    }
}
