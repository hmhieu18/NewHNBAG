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

    public String getUsernamesInString() {
        StringBuilder res = new StringBuilder();
        res.append("Including: you");
        for (int i = 1; i < usernames.length; i++) {
            res.append(usernames[i]);
            if (i != usernames.length - 1) res.append(", ");
        }
        return res.toString();
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
