package com.rusd.game.observers;

import com.rusd.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created by shane on 7/11/15.
 */
public class Subject {

    private ArrayList<Observer> observers = new ArrayList<>();

    public void ClearList() {
        observers.clear();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    public void onNotify(Entity entity, Event.Events event) {
        observers.stream().forEach(o -> o.onNotify(entity, event));
    }
}
