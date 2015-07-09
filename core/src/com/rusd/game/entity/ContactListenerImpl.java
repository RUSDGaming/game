package com.rusd.game.entity;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.esotericsoftware.minlog.Log;

/**
 * Created by shane on 7/8/15.
 */
public class ContactListenerImpl implements ContactListener {
    public static final String tag = ContactListenerImpl.class.getSimpleName();

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() != null)
            if (contact.getFixtureA().getUserData() instanceof Entity) {
                Log.info(tag, "there was a contact!!");
            }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
