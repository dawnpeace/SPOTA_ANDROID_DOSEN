package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

public class UploadedSemester {
    private int preoutline_count;
    private String semester;

    public UploadedSemester(int preoutline_count, String semester) {
        this.preoutline_count = preoutline_count;
        this.semester = semester;
    }

    public int getPreoutline_count() {
        return preoutline_count;
    }

    public String getSemester() {
        return semester;
    }
}
