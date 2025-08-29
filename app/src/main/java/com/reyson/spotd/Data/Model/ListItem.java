package com.reyson.spotd.Data.Model;

public class ListItem {
    private String id;
    private String label;
    private int points;

    public ListItem(String id, String label, int points) {
        this.id = id;
        this.label = label;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return label;
    }
}
