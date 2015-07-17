package com.rusd.game.entity;

/**
 * Created by shane on 7/2/15.
 */
public class StatsComponent {
    public static final String tag = StatsComponent.class.getSimpleName();

    private Entity entity;
    private Float health = 10f;
    private Float maxSpeed = 20f;
    private Float armor = 0f;
    private Float acceleration = 15f;
    private Long reloadTime = 100L;
    private Long lastAttack = 0L;
    private Float Damage = 0f;

    public Float getDamage() {
        return Damage;
    }

    public void setDamage(Float damage) {
        Damage = damage;
    }

    public Long getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(Long reloadTime) {
        this.reloadTime = reloadTime;
    }

    public Long getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(Long lastAttack) {
        this.lastAttack = lastAttack;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Float getHealth() {
        return health;
    }

    public void setHealth(Float health) {
        this.health = health;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Float getArmor() {
        return armor;
    }

    public void setArmor(Float armor) {
        this.armor = armor;
    }

    public Float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Float acceleration) {
        this.acceleration = acceleration;
    }


    public void damageEntity(Float damage) {

        float damagDealt = damage - armor;
        if (damagDealt > 0) {
            health -= damagDealt;
            if (health < 0) {
                entity.destroyMe = true;
            }
        }


    }
}
