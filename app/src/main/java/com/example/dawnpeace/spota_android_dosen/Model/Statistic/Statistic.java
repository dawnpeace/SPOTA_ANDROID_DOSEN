package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

import java.util.List;

public class Statistic {
    private List<Expertise> expertise;
    private List<Lecturer> lecturers;
    private Status status;

    public Statistic(List<Expertise> expertise, List<Lecturer> lecturers, Status status) {
        this.expertise = expertise;
        this.lecturers = lecturers;
        this.status = status;
    }

    public List<Expertise> getExpertise() {
        return expertise;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public Status getStatus() {
        return status;
    }
}
