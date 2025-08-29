package com.reyson.spotd.Data.Model;

import java.util.ArrayList;

public class GroupedNotification {
    private String header;
    private ArrayList<Notify> data;

    public GroupedNotification(String header, ArrayList<Notify> data) {
        this.header = header;
        this.data = data;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<Notify> getData() {
        return data;
    }
}

