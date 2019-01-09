package com.example.dawnpeace.spota_android_dosen.Model;

public class Message {
    private boolean successful;
    private String error_message;
    private User user;

    public Message(boolean successful, String error_message, User user) {
        this.successful = successful;
        this.error_message = error_message;
        this.user = user;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getError_message() {
        return error_message;
    }

    public User getUser() {
        return user;
    }
}
