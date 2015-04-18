package ru.nsu.ccfit.tabakov.artchallenge;

/**
 * Created by Константин on 18.04.2015.
 */
public class Painter {
    private int id;
    private String name;
    private int paintings;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPictureQuantity() {
        return paintings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaintings(int paintings) {
        this.paintings = paintings;
    }
}

