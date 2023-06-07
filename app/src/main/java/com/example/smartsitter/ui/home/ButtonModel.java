package com.example.smartsitter.ui.home;

public class ButtonModel {
    private String name;

    public ButtonModel() {
        // Required empty public constructor for Firestore
    }

    public ButtonModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

