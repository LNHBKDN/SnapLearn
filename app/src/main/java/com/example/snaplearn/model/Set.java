package com.example.snaplearn.model;

import java.util.ArrayList;

public class Set {
    private String ID;
    private String Name;
    private String Description;

    private ArrayList<FlashCard> ListFlashCard;

    public Set() {
    }

    public Set(String ID, String name, String description, ArrayList<FlashCard> listFlashCard) {
        this.ID = ID;
        Name = name;
        Description = description;
        ListFlashCard = listFlashCard;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<FlashCard> getListFlashCard() {
        return ListFlashCard;
    }

    public void setListFlashCard(ArrayList<FlashCard> listFlashCard) {
        ListFlashCard = listFlashCard;
    }
}
