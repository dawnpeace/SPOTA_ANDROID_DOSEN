package com.example.dawnpeace.spota_android_dosen.Model;

public class Approval {
    private int id;
    private String name;
    private String approval;
    private String date;

    public Approval(int id, String name, String approval, String date) {
        this.id = id;
        this.name = name;
        this.approval = approval;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApproval(){
        String approve = "";
        switch (approval) {
            case "approve":
                approve = "menyetujui";
                break;
            case "disapprove":
                approve = "menolak";
                break;
        }
        return approve;
    }

    public String getDate() {
        return date;
    }
}
