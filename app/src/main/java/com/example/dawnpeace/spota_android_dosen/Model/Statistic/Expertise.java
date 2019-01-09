package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

public class Expertise {
    private int expertise_id;
    private String expertise_name;
    private int alltime_count;
    private int semester_count;

    public Expertise(int expertise_id, String expertise_name, int alltime_count, int semester_count) {
        this.expertise_id = expertise_id;
        this.expertise_name = expertise_name;
        this.alltime_count = alltime_count;
        this.semester_count = semester_count;
    }

    public int getExpertise_id() {
        return expertise_id;
    }

    public String getExpertise_name() {
        return expertise_name;
    }

    public int getAlltime_count() {
        return alltime_count;
    }

    public int getSemester_count() {
        return semester_count;
    }
}
