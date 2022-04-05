package com.example.kampung.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class SmolRequest {
    public String username;
    public String order;

    public SmolRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public SmolRequest(String username, String order) {
        this.username = username;
        this.order = order;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}


