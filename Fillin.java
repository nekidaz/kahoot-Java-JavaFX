package com.example.p2;

public class Fillin extends Questions {
    @Override
    public String toString() {
        return getDescription().replace("{blank}","_____");
    }
}
