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
    public Boolean isAccepted;
    public User acceptedBy;
    public Boolean isDelivered;

    public Request() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Request(
        User user, Order order, Long time, Long expireTime, String dest, Boolean isAccepted, Boolean isDelivered, User acceptedBy
    ) {
        this.user = user;
        this.order = order;
        this.time = time;
        this.expireTime = expireTime;
        this.dest = dest;
        this.isAccepted = isAccepted;
        this.isDelivered = isDelivered;
        this.acceptedBy=acceptedBy;
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
        byte tmpIsAccepted = in.readByte();
        isAccepted = tmpIsAccepted == 0 ? null : tmpIsAccepted == 1;
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

    public User getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(User acceptedBy) {
        this.acceptedBy = acceptedBy;
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
        parcel.writeByte((byte) (isAccepted == null ? 0 : isAccepted ? 1 : 2));
        parcel.writeByte((byte) (isDelivered == null ? 0 : isDelivered ? 1 : 2));
    }

    public String getTimeInString(){
        long currentTime = System.currentTimeMillis();
        int pastSeconds = (int) (currentTime-time)/1000;
        int day = pastSeconds/86400;
        int hour = (pastSeconds%86400)/3600;
        int minute = (pastSeconds%86400%3600)/60;
        String s = minute+" min";
        if (hour!=0) s = hour+" h "+s;
        if (day!=0) s = day+" d "+s;
        return s;
    }
}
