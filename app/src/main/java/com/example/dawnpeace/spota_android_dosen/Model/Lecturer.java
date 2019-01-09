package com.example.dawnpeace.spota_android_dosen.Model;

public class Lecturer {
    private int id;
    private String name;
    private String identity_number;

    public Lecturer(int id, String name, String identity_number) {
        this.id = id;
        this.name = name;
        this.identity_number = identity_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }
}
