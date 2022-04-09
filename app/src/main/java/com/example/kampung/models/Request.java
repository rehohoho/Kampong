package com.example.kampung.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties // ignore class fields not mapped to properties
public class Request implements Parcelable {

    public User user;
    public Order order;
    public Long time;
    public Long expireTime;
    public String dest;
    public String isAccepted;
    public Boolean isDelivered;

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Request(
        User user, Order order, Long time, Long expireTime, String dest, String isAccepted, Boolean isDelivered
    ) {
        this.user = user;
        this.order = order;
        this.time = time;
        this.expireTime = expireTime;
        this.dest = dest;
        this.isAccepted = isAccepted;
        this.isDelivered = isDelivered;
    }

    protected Request(Parcel in) {
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readLong();
        }
        if (in.readByte() == 0) {
            expireTime = null;
        } else {
            expireTime = in.readLong();
        }
        dest = in.readString();
        isAccepted=in.readString();

        byte tmpIsDelivered = in.readByte();
        isDelivered = tmpIsDelivered == 0 ? null : tmpIsDelivered == 1;
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

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

    public String getAccepted() {
        return isAccepted;
    }

    public void setAccepted(String accepted) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (time == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(time);
        }
        if (expireTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(expireTime);
        }
        parcel.writeString(dest);
        parcel.writeString(isAccepted);
        parcel.writeByte((byte) (isDelivered == null ? 0 : isDelivered ? 1 : 2));
    }
}
