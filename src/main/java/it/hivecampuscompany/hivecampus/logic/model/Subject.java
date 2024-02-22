package it.hivecampuscompany.hivecampus.logic.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected final List<Observer> observers = new ArrayList<>();
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
