package com.example.snaplearn.model;

public class FlashCard {
    private String ID;
    private String IDSet;
    private String term;
    private String definition;

    public FlashCard() {
    }

    public FlashCard(String ID, String IDSet, String term, String definition) {
        this.ID = ID;
        this.IDSet = IDSet;
        this.term = term;
        this.definition = definition;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDSet() {
        return IDSet;
    }

    public void setIDSet(String IDSet) {
        this.IDSet = IDSet;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
