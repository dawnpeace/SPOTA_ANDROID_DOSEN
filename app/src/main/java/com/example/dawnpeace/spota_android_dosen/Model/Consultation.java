package com.example.dawnpeace.spota_android_dosen.Model;

public class Consultation {

    private String title;
    private String name;
    private String identity_number;
    private String section;
    private String created_at;
    private String last_commented;
    private int consultation_id;
    private String localSection;

    public Consultation(String title, String name, String identity_number, String section, String created_at, String last_commented,int consultation_id) {
        this.title = title;
        this.name = name;
        this.identity_number = identity_number;
        this.section = section;
        this.created_at = created_at;
        this.last_commented = last_commented;
        this.consultation_id = consultation_id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getSection() {
        String _section = "";
        switch(section){
            case "first":
                _section = "BAB I";
                break;
            case "second":
                _section = "BAB II";
                break;
            case "third":
                _section = "BAB III";
                break;
            case "fourth":
                _section = "BAB IV";
                break;
            case "fifth":
                _section = "BAB V";
                break;
        }
        return _section;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getLast_commented() {
        return last_commented;
    }

    public int getId(){
        return consultation_id;
    }

    public String getLocalSection() {
        return section;
    }
}
