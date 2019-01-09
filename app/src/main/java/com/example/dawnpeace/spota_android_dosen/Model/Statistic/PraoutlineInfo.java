package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

import com.example.dawnpeace.spota_android_dosen.Model.Expertise;
import com.example.dawnpeace.spota_android_dosen.Model.Lecturer;

import java.util.List;

public class PraoutlineInfo {
    private List<Lecturer > lecturers;
    private List<Expertise> expertises;
    private String preoutline_title;

    public PraoutlineInfo(List<Lecturer> lecturers, List<Expertise> expertises, String preoutline_title) {
        this.lecturers = lecturers;
        this.expertises = expertises;
        this.preoutline_title = preoutline_title;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public List<Expertise> getExpertises() {
        return expertises;
    }

    public String getPreoutline_title() {
        return preoutline_title;
    }
}
