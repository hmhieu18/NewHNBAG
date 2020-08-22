package com.example.hnbag;

public class Group {
    public Group(String _id, String[] userIDs) {
        this._id = _id;
        this.userIDs = userIDs;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String[] getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(String[] userIDs) {
        this.userIDs = userIDs;
    }

    private String _id;
    private String[] userIDs;
}
