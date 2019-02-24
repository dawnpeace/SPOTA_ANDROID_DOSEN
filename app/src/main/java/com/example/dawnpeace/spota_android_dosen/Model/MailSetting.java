package com.example.dawnpeace.spota_android_dosen.Model;

public class MailSetting {
    private String consultation;
    private String new_draft;
    private String draft_review;

    public MailSetting(String consultation, String new_draft, String draft_review) {
        this.consultation = consultation;
        this.new_draft = new_draft;
        this.draft_review = draft_review;
    }

    public boolean getConsultation() {
        return consultation.equals("on");
    }

    public boolean getNew_draft() {
        return new_draft.equals("on");
    }

    public boolean getDraft_review() {
        return draft_review.equals("on");
    }
}
