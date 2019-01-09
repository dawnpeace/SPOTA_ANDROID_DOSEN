package com.example.dawnpeace.spota_android_dosen.Model;

import java.util.List;

public class DraftApproval {
    private int upvote_count;
    private int downvote_count;
    private List<Approval> approvals;

    public DraftApproval(int upvote_count, int downvote_count, List<Approval> approvals) {
        this.upvote_count = upvote_count;
        this.downvote_count = downvote_count;
        this.approvals = approvals;
    }

    public String getUpvote_count(){
        return String.valueOf(upvote_count);
    }

    public String getDownvote_count(){
        return String.valueOf(downvote_count);
    }

    public List<Approval> getApprovals() {
        return approvals;
    }
}
