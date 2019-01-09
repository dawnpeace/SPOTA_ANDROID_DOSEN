package com.example.dawnpeace.spota_android_dosen.Model;

public class ConsultationReview {
    private int id;
    private String message;
    private String date_locale;
    private String name;
    private String identity_number;
    private String created_at;

    public ConsultationReview(int id, String message, String date_locale, String name, String identity_number, String created_at) {
        this.id = id;
        this.message = message;
        this.date_locale = date_locale;
        this.name = name;
        this.identity_number = identity_number;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate_locale() {
        return date_locale;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getCreated_at() {
        return created_at;
    }
}
