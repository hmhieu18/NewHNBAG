package com.example.hnbag;

import com.google.android.gms.maps.model.LatLng;

public class Food {
    private String _description;
    private int _logoID;

    public Food(String description, int logoID) {
        this._description = description;
        this._logoID = logoID;
    }


    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public int getLogoID() {
        return _logoID;
    }

    public void setLogoID(int logoID) {
        this._logoID = logoID;
    }


}
