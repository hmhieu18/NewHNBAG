package com.example.hnbag;

import java.util.ArrayList;

public class Profile {
    public static ArrayList<Food> favoriteFoods;
    public static String username;
    public static String password;
    public static String token;
    public static String Id;
    public static ArrayList<Root.Results> favoritePlaces;
    public static ArrayList<Food> getFavoriteFoods() {
        return favoriteFoods;
    }

    public static String getUsername() {
        return username;
    }

    public static ArrayList<Root.Results> getFavoritePlaces() {
        return favoritePlaces;
    }



    public static void setFavoriteFoods(ArrayList<Food> favoriteFoods) {
        Profile.favoriteFoods = favoriteFoods;
    }

    public static void setUsername(String username) {
        Profile.username = username;
    }

    public static void setFavoritePlaces(ArrayList<Root.Results> favoritePlaces) {
        Profile.favoritePlaces = favoritePlaces;
    }
}
