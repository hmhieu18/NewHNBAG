package com.example.hnbag;

import java.util.ArrayList;

public class Profile {
    public ArrayList<Food> favoriteFoods=new ArrayList<>();
    public String username;
    public String json_favorite_places;
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

    public String password;
    public String sessionToken;
    public String _id;
    public ArrayList<Root.Results> favoritePlaces=new ArrayList<>();

    public ArrayList<Food> getFavoriteFoods() {
        return favoriteFoods;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Root.Results> getFavoritePlaces() {
        return favoritePlaces;
    }


    public void setFavoriteFoods(ArrayList<Food> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFavoritePlaces(ArrayList<Root.Results> favoritePlaces) {
        this.favoritePlaces = favoritePlaces;
    }
}
