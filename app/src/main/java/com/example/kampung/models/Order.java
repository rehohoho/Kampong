package com.example.kampung.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties // ignore class fields not mapped to properties
public class Order {

    public String location;
    public String vendor;
    public String food;
    public Boolean meal;
    public String remarks;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Order(
        String location, String vendor, String food, Boolean meal, String remarks
    ) {
        this.location = location;
        this.vendor = vendor;
        this.food = food;
        this.meal = meal;
        this.remarks = remarks;
    }

    @Exclude // excludes field from database
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("location", location);
        result.put("vendor", vendor);
        result.put("food", food);
        result.put("meal", meal.toString());
        result.put("remarks", remarks);
        return result;
    }

    @Exclude
    @Override
    public String toString() {
        return "Order{" +
                "location='" + location + '\'' +
                ", vendor='" + vendor + '\'' +
                ", food='" + food + '\'' +
                ", meal=" + meal +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
