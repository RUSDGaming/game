package com.rusd.game.entity;

/**
 * Created by shane on 7/10/15.
 */
public class EntityContactHandler {
    public final String tag = EntityContactHandler.class.getSimpleName();

    private Entity entity;

    public EntityContactHandler(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void handleContact(Entity contactThatHitMe) {


        // if my parent == contact's parent, ignore contact
        // note that the parent of a player is itself.

        if (contactThatHitMe.getParentEntity().equals(entity.getParentEntity())) {
            //Log.info(tag, "Hit my parent");
            return;
        }

        if (contactThatHitMe.entityType.equals(Entity.EntityType.BULLET)) {
            // Log.info(tag, "A Bullet hit me");
            entity.statsComponent.damageEntity(contactThatHitMe.getStatsComponent().getDamage());
        }

        // if the type of entity that hit was not a bullet,
        // and I am a bullet I should kill myself.
        // Kappa...
        if (!contactThatHitMe.getEntityType().equals(Entity.EntityType.BULLET)) {
            if (entity.getEntityType().equals(Entity.EntityType.BULLET)) {
                // Log.info(tag,"I'm a bullet, And i hit something that was not a bulet");
                entity.setDestroyMe(true);
                entity.getStatsComponent().setHealth(-1f);
            }
        }
    }
}