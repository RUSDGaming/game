package com.rusd.game.entity;

import com.esotericsoftware.minlog.Log;

/**
 * Created by shane on 7/3/15.
 */
public class DeathTimerComponent {
    public static final String tag = DeathTimerComponent.class.getSimpleName();

    private Long lifeSpan;
    private Entity entity;
    private boolean running;

    public DeathTimerComponent(Long lifeSpan, Entity entity) {
        this.lifeSpan = lifeSpan;
        this.entity = entity;

        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    running = true;
                    Thread.sleep(lifeSpan);
                    running = false;
                    entity.statsComponent.setHealth(-1);
                    Log.debug(tag, "died");
                } catch (InterruptedException e) {
                    Log.error(tag, e.toString());
                    entity.statsComponent.setHealth(-1);
                    running = false;
                }
            }
        });
        timer.start();
    }

    public Long getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(Long lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
