package com.example.p2;



import java.util.ArrayList;
import java.util.Collections;

public class TEST  extends Questions {
    private String[] optipons = new String[4];
    private ArrayList<String> labels = new ArrayList<>();
    private int numOfoptios;


    public TEST() {
    }

    public String getOptionsAt(int index) {
        return optipons[index];
    }

    public void setOptipons(String[] optipons) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < optipons.length; i++) {
            this.optipons[i] = optipons[i];
            list.add(optipons[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 4; i++) {
            this.optipons[i] = list.get(i);
        }

    }

    @Override
    public String toString() {
        String[] labels = {"A", "B", "C", "D"};
        String test = getDescription() + "\n";
        for (int i = 0; i < 4; i++) {
            test += labels[i] + " ) " + getOptionsAt(i) + "\n";
        }
        return test;
    }
}
