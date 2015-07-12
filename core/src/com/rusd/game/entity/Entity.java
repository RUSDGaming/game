package com.rusd.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.rusd.game.observers.Subject;

/**
 * Created by shane on 7/2/15.
 */
public class Entity {

    public enum EntityType {
        PLAYER, BULLET, BUILDING
    }

    public Body bodyComponent;
    public StatsComponent statsComponent;
    public RenderComponent renderComponent;
    public String name;
    public EntityContactHandler entityContactHandler;
    public EntityType entityType;
    public Entity parentEntity;
    // currently doesnt do aything
    protected Boolean destroyMe = false;
    public Boolean disconnect = false;

    @Deprecated// This is only a temp use case...
    private Color color = Color.PINK;

    public Entity(Subject subject) {
        entityContactHandler = new EntityContactHandler(this, subject);
    }

    public ScoreComponent getScoreComponent() {
        return scoreComponent;
    }

    public void setScoreComponent(ScoreComponent scoreComponent) {
        this.scoreComponent = scoreComponent;
    }

    public ScoreComponent scoreComponent;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public void setRenderComponent(RenderComponent renderComponent) {
        this.renderComponent = renderComponent;
    }

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

    public Entity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(Entity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public EntityContactHandler getEntityContactHandler() {
        return entityContactHandler;
    }

    public void setEntityContactHandler(EntityContactHandler entityContactHandler) {
        this.entityContactHandler = entityContactHandler;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Boolean getDestroyMe() {
        return destroyMe;
    }

    public void setDestroyMe(Boolean destroyMe) {
        this.destroyMe = destroyMe;
    }
}
