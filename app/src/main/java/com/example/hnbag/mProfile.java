package com.example.hnbag;

import java.util.ArrayList;

public class mProfile {
    public String password;
    public String sessionToken;
    public String _id;
    public ArrayList<Results> favoritePlaces = new ArrayList<>();
    public ArrayList<Food> favoriteFoods = new ArrayList<>();
    public ArrayList<Group> listGroup = new ArrayList<>();
    public String username;
    public String json_favorite_places;
    public String json_group;
    public String json_favorite_foods;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public ArrayList<Food> getFavoriteFoods() {
        return favoriteFoods;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Results> getFavoritePlaces() {
        return favoritePlaces;
    }


    public void setFavoriteFoods(ArrayList<Food> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFavoritePlaces(ArrayList<Results> favoritePlaces) {
        this.favoritePlaces = favoritePlaces;
    }
}
