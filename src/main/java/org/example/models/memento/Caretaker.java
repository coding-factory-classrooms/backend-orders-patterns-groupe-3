package org.example.models.memento;

import org.example.models.Order;

import java.util.ArrayList;

public class Caretaker {
    private ArrayList<Object> savedStates = new ArrayList();

    public void addMemento(Object m) { savedStates.add(m); }
    public Object getMemento(int index) { return savedStates.get(index); }

    public ArrayList getSavedStates() {
        return savedStates;
    }
}
