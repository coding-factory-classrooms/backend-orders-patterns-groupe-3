package org.example.models.memento;

public class Originator {
    private Object state;

    public void set(Object state) {
        System.out.println("Originator: etat affecte a: "+state);
        this.state = state;
    }

    public Object getState() {
        return state;
    }

    public Object saveToMemento() {
        System.out.println("Originator: sauvegarde dans le memento.");
        return new Memento(state);
    }


    public Object restoreFromMemento(Object m) {
        Object state = new Object();
        if (m instanceof Memento) {
            Memento memento = (Memento)m;
            state = memento.getSavedState();
        }
        return state;
    }

    private static class Memento {
        private Object state;

        public Memento(Object stateToSave) { state = stateToSave; }
        public Object getSavedState() { return state; }
    }
}
