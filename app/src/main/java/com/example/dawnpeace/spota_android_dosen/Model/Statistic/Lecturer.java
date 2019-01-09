package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

public class Lecturer {
    private int user_id;
    private String lecturer;
    private int supervised_allttime;
    private int supervised_semester;
    private int examined_alltime;
    private int examined_semester;


    public Lecturer(int user_id, String lecturer, int supervised_allttime, int supervised_semester, int examined_alltime, int examined_semester) {
        this.user_id = user_id;
        this.lecturer = lecturer;
        this.supervised_allttime = supervised_allttime;
        this.supervised_semester = supervised_semester;
        this.examined_alltime = examined_alltime;
        this.examined_semester = examined_semester;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getLecturer() {
        return lecturer;
    }

    public int getSupervised_allttime() {
        return supervised_allttime;
    }

    public int getSupervised_semester() {
        return supervised_semester;
    }

    public int getExamined_alltime() {
        return examined_alltime;
    }

    public int getExamined_semester() {
        return examined_semester;
    }
}
