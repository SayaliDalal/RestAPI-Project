package com.example.sayali_branch_project.models;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class SendMessage {
    @NotNull
    @SerializedName("thread_id")
    private int threadId;
    @NotNull
    @SerializedName("body")
    private String body;

    @NotNull
    public String getBody() {
        return body;
    }

    public SendMessage(int threadId, String body) {
        this.threadId = threadId;
        this.body = body;
    }
}
