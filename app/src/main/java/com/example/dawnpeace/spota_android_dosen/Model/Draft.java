package com.example.dawnpeace.spota_android_dosen.Model;

import java.util.List;

public class Draft {
    private String title;
    private String file;
    private String description;
    private List<Approval> approvals;
    private int upvote_count;
    private int downvote_count;
    private String status;
    private String my_approval;

    public Draft(String title, String file, String description, List<Approval> approvals, int upvote_count, int downvote_count,String status, String my_approval) {
        this.title = title;
        this.file = file;
        this.description = description;
        this.approvals = approvals;
        this.upvote_count = upvote_count;
        this.downvote_count = downvote_count;
        this.status = status;
        this.my_approval = my_approval;
    }

    public String getTitle() {
        return title;
    }

    public String getFile() {
        return file;
    }

    public String getDescription() {
        return description;
    }

    public List<Approval> getApprovals() {
        return approvals;
    }

    public int getUpvote_count() {
        return upvote_count;
    }

    public int getDownvote_count() {
        return downvote_count;
    }

    public String getStatus(){
        return status;
    }

    public String getLocalizedStatus(){
        String localized_status ="";
        switch (status){
            case "open":
                localized_status = "Terbuka";
                break;
            case "closed":
                localized_status = "Gugur";
                break;
            case "rejected":
                localized_status = "Ditolak";
                break;
            case "approved":
                localized_status = "Diterima";
                break;
        }
        return localized_status;
    }


    public String getMy_approval() {
        return my_approval;
    }
}
