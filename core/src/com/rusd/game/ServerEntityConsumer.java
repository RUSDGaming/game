package com.rusd.game;

import com.rusd.game.entity.Entity;

import java.util.function.Consumer;

/**
 * Created by shane on 7/5/15.
 */
public class ServerEntityConsumer implements Consumer<Entity> {

    @Override
    public void accept(Entity entity) {

    }

    @Override
    public Consumer<Entity> andThen(Consumer<? super Entity> consumer) {
        return null;
    }
}
