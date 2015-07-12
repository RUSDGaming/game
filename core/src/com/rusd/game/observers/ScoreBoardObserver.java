package com.rusd.game.observers;

import com.rusd.game.entity.Entity;

/**
 * Created by shane on 7/11/15.
 */
public class ScoreBoardObserver implements Observer {


    @Override
    public void onNotify(Entity entity, Event.Events events) {
        switch (events) {
            case PLAYER_DEATH: {
                entity.getScoreComponent().addDeath(1);
                break;
            }
            case PLAYER_KILL: {
                entity.getScoreComponent().addKills(1);
                break;
            }
        }


    }
}
