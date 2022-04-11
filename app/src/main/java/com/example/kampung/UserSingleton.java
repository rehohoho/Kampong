package com.example.kampung;

import com.example.kampung.models.User;

public class UserSingleton {

    private static UserSingleton instance=null;
    private User user;

    private UserSingleton(User user){
        this.user=user;

    }

    public static UserSingleton getInstance(User user){
        if(instance==null){
            instance= new UserSingleton(user);
        }

        if(instance.getUser()!=user){
            instance=new UserSingleton(user);
        }
        return instance;

    }

    public static UserSingleton getInstance(){

        return instance;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
