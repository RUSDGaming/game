package com.rusd.game.observers;

import com.rusd.game.entity.Entity;

/**
 * Created by shane on 7/11/15.
 */
public interface Observer {
    void onNotify(Entity entity, Event.Events events);
}
