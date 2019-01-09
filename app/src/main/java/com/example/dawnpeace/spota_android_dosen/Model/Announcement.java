package com.example.dawnpeace.spota_android_dosen.Model;

public class Announcement {
    private int id;
    private boolean is_read;
    private String title;
    private String date;
    private String content;
    private String sender;

    public Announcement(int id, boolean is_read, String title, String date, String content, String sender) {
        this.id = id;
        this.is_read = is_read;
        this.title = title;
        this.date = date;
        this.content = content;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public boolean isRead() {
        return is_read;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
}
