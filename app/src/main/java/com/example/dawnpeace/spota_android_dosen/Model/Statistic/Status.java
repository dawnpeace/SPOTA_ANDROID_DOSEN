package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

import java.util.List;

public class Status {

    private int current_open;
    private List<UploadedSemester> uploaded_by_semester;
    private PreoutlineCount alltime;
    private PreoutlineCount semester;

    public Status(List<UploadedSemester> uploaded_by_semester, PreoutlineCount alltime, PreoutlineCount semester, int current_open) {
        this.uploaded_by_semester = uploaded_by_semester;
        this.alltime = alltime;
        this.semester = semester;
        this.current_open = current_open;
    }

    public List<UploadedSemester> getUploaded_by_semester() {
        return uploaded_by_semester;
    }

    public PreoutlineCount getAlltime() {
        return alltime;
    }

    public PreoutlineCount getSemester() {
        return semester;
    }

    public int getCurrent_open() {
        return current_open;
    }
}
