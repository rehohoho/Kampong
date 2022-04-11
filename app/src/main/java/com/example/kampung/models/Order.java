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
        String location, String vendor, String food, Boolean meal
    ) {
        this.location = location;
        this.vendor = vendor;
        this.food = food;
        this.meal = meal;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Boolean getMeal() {
        return meal;
    }

    public void setMeal(Boolean meal) {
        this.meal = meal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
