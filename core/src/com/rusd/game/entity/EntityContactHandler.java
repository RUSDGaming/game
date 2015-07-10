package com.rusd.game.entity;

/**
 * Created by shane on 7/10/15.
 */
public class EntityContactHandler {

    private StatsComponent statsComponent;
    private Entity entity;


    public EntityContactHandler(Entity entity, StatsComponent statsComponent) {
        this.entity = entity;
        this.statsComponent = statsComponent;
    }


    public void handleContact(Entity contactThatHitMe) {
        if (contactThatHitMe.equals(entity)) {
            return;
        }

        statsComponent.damageEntity(contactThatHitMe.getStatsComponent().getDamage());


    }

    public StatsComponent getStatsComponent() {
        return statsComponent;
    }

    public void setStatsComponent(StatsComponent statsComponent) {
        this.statsComponent = statsComponent;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
