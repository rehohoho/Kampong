package com.example.kampung.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties // ignore class fields not mapped to properties
public class Request {

    public User user;
    public Order order;
    public Long time;
    public Long expireTime;
    public String dest;
    public Boolean isAccepted;
    public Boolean isDelivered;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Boolean getDelivered() {
        return isDelivered;
    }

    public void setDelivered(Boolean delivered) {
        isDelivered = delivered;
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

    @Override
    public String toString() {
        return "Request{" +
                "user=" + user +
                ", order=" + order +
                ", time=" + time +
                ", expireTime=" + expireTime +
                ", dest='" + dest + '\'' +
                ", isAccepted=" + isAccepted +
                ", isDelivered=" + isDelivered +
                '}';
    }
}
