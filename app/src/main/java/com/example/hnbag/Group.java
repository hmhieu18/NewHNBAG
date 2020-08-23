package com.example.hnbag;

import java.util.ArrayList;
import java.util.Arrays;

public class Group {


    public ArrayList<Results> getFavoritePlaces() {
        return commonFavoritePlaces;
    }

    public void setCommonFavoritePlaces(ArrayList<Results> favoritePlaces) {
        this.commonFavoritePlaces = favoritePlaces;
    }

    public ArrayList<Food> getCommonFavoriteFoods() {
        return this.commonFavoriteFoods;
    }

    public void setCommonFavoriteFoods(ArrayList<Food> commonFavoriteFoods) {
        this.commonFavoriteFoods = commonFavoriteFoods;
    }

    public ArrayList<Results> commonFavoritePlaces = MainActivity.mProfile.favoritePlaces;
    public ArrayList<Food> commonFavoriteFoods = MainActivity.mProfile.favoriteFoods;

    public void addUser(String username) {
        usernames = Arrays.copyOf(usernames, usernames.length + 1);
        usernames[usernames.length - 1] = username;
    }

    public Group(String[] usernames, String groupName) {
        this.usernames = usernames;
        this.groupName = groupName;
    }

    private String[] usernames;
    private String groupName;

    public String[] getUsernames() {
        return usernames;
    }

    public void setUsernames(String[] usernames) {
        this.usernames = usernames;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
