package com.example.dawnpeace.spota_android_dosen.Model;

public class Login {
    private String access_token;
    private String token_type;
    private String type;
    private int error;

    public Login(String access_token, String token_type, String type, int error) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.type = type;
        this.error = error;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getType() {
        return type;
    }

    public int getError() {
        return error;
    }
}
