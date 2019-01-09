package com.example.dawnpeace.spota_android_dosen.Model;

public class User {
    private String id;
    private String name;
    private String identity_number;
    private String email;
    private int major_id;
    private String photo;
    private boolean is_majorheadmaster;


    public User(String id, String name, String identity_number, String email, int major_id, String photo, boolean is_majorheadmaster){
        this.email = email;
        this.id = id;
        this.identity_number = identity_number;
        this.major_id = major_id;
        this.name = name;
        this.photo = photo;
        this.is_majorheadmaster = is_majorheadmaster;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl(){
        return photo;
    }

    public String getId() {
        return id;
    }

    public int getMajor_id() {
        return major_id;
    }

    public boolean isMajorheadmaster() {
        return is_majorheadmaster;
    }

}
