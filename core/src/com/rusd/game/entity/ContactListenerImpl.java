package com.rusd.game.entity;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shane on 7/8/15.
 */
public class ContactListenerImpl implements ContactListener {
    public static final String tag = ContactListenerImpl.class.getSimpleName();


    @Override
    public void beginContact(Contact contact) {
//        Log.info(tag, "there was a contact!!");

        List<Entity> entities = getEntities(contact);
        entities.get(0).getEntityContactHandler().handleContact(entities.get(1));
        entities.get(1).getEntityContactHandler().handleContact(entities.get(0));



    }

    public List<Entity> getEntities(Contact contact) {

        List<Entity> entities = new ArrayList<>();

        Object userData1 = contact.getFixtureA().getBody().getUserData();
        Object userData2 = contact.getFixtureB().getBody().getUserData();

        if (userData1 != null) {
            if (userData1 instanceof Entity) {
                entities.add((Entity) userData1);
            }
        }
        if (userData2 != null) {
            if (userData2 instanceof Entity) {
                entities.add((Entity) userData2);
            }
        }


        return entities;


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
