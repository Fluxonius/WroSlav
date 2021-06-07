package com.example.wroslav.dataClasses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user class to keep track of registered user
 * and their data (profile info)
 */

public class User {

    private String email;
    private String nickName;
    private List SavedEvents;
    private List SavedPlace;
    private List SavedNews;

    public User(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List getSavedEvents() {
        return SavedEvents;
    }

    public void setSavedEvents(List savedEvents) {
        SavedEvents = savedEvents;
    }

    public List getSavedPlace() {
        return SavedPlace;
    }

    public void setSavedPlace(List savedPlace) {
        SavedPlace = savedPlace;
    }

    public List getSavedNews() {
        return SavedNews;
    }

    public void setSavedNews(List savedNews) {
        SavedNews = savedNews;
    }
}