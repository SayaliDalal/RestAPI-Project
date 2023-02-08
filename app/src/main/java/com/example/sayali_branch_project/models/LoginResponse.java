package com.example.sayali_branch_project.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("auth_token")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }
}
