package com.example.dawnpeace.spota_android_dosen.Model.Statistic;

public class PreoutlineCount {
    private int approved;
    private int rejected;
    private int closed;

    public PreoutlineCount(int approved, int rejected, int closed) {
        this.approved = approved;
        this.rejected = rejected;
        this.closed = closed;
    }


    public int getApproved() {
        return approved;
    }

    public int getRejected() {
        return rejected;
    }

    public int getClosed() {
        return closed;
    }
}
