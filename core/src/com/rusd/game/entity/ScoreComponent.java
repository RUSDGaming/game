package com.rusd.game.entity;

/**
 * Created by shane on 7/11/15.
 */
public class ScoreComponent {
    public static final String tag = ScoreComponent.class.getSimpleName();
    private Entity entity;

    private int kills = 0;
    private int deaths = 0;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public ScoreComponent(Entity e) {
        this.entity = e;
    }

    public void addDeath(int i) {
        this.deaths += i;
        //Log.info(tag,"current death count: "+ deaths);
    }

    public void addKills(int i) {
        this.kills += i;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
