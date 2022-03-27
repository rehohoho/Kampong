package com.example.kampung.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties // ignore class fields not mapped to properties
public class Request {

    User user;
    Order order;
    Long time;
    Long expireTime;
    String dest;
    Boolean isAccepted;
    Boolean isDelivered;

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Request(
        User user, Order order, Long time, Long expireTime, String dest, Boolean isAccepted, Boolean isDelivered
    ) {
        this.user = user;
        this.order = order;
        this.time = time;
        this.expireTime = expireTime;
        this.dest = dest;
        this.isAccepted = isAccepted;
        this.isDelivered = isDelivered;
    }

    @Exclude // excludes field from database
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("order", order);
        result.put("time", time);
        result.put("expireTime", expireTime);
        result.put("dest", dest);
        result.put("accepted", isAccepted);
        result.put("delivered", isDelivered);
        return result;
    }

}
