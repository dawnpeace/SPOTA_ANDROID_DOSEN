package com.example.dawnpeace.spota_android_dosen.Model;

public class Schedule {
    private String name;
    private String identity_number;
    private String date;
    private String type;
    private String time;
    private String localized_date;

    public Schedule(String name, String identity_number, String date, String time, String localized_date, String type) {
        this.name = name;
        this.identity_number = identity_number;
        this.date = date;
        this.time = time;
        this.localized_date = localized_date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocalized_date() {
        return localized_date;
    }

    public String getType() {
        return type;
    }
}
