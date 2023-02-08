package com.example.sayali_branch_project.models;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    public int id;
    @SerializedName("thread_id")
    public int threadId;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("body")
    public String body;
    @SerializedName("timestamp")
    public String timestamp;
    @SerializedName("agent_id")
    public String agentId;

    public Message(int id, int threadId, String userId, String body, String timestamp, String agentId) {
        this.id = id;
        this.threadId = threadId;
        this.userId = userId;
        this.body = body;
        this.timestamp = timestamp;
        this.agentId = agentId;
    }

}
