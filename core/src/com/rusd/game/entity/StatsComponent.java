package com.rusd.game.entity;

/**
 * Created by shane on 7/2/15.
 */
public class StatsComponent {
    public static final String tag = StatsComponent.class.getSimpleName();

    private Entity entity;
    private Integer health;
    private Float maxSpeed;
    private Integer armor;
    private Float acceleration;
    private Long reloadTime;
    private Long lastAttack = 0L;

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

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getArmor() {
        return armor;
    }

    public void setArmor(Integer armor) {
        this.armor = armor;
    }

    public Float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Float acceleration) {
        this.acceleration = acceleration;
    }
}
