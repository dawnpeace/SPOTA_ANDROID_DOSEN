package com.example.dawnpeace.spota_android_dosen.Model;

public class Praoutline {
    private String title;
    private String identity_number;
    private String name;
    private String created_at;
    private int id;
    private String picture;
    private int upvote;
    private int downvote;
    private int review_count;

    public Praoutline(String title, String identity_number, String name, String created_at, int id, String picture, int upvote, int downvote, int review_count) {
        this.title = title;
        this.identity_number = identity_number;
        this.name = name;
        this.created_at = created_at;
        this.id = id;
        this.picture = picture;
        this.upvote = upvote;
        this.downvote = downvote;
        this.review_count = review_count;
    }

    public String getTitle() {
        return title;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public int getUpvote() {
        return upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public int getReview_count() {
        return review_count;
    }
}
