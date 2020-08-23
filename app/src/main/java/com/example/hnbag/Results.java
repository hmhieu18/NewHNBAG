package com.example.hnbag;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Results
{
    private String business_status;

    private String formatted_address;

    public Geometry getGeometry() {
        return geometry;
    }

    private Geometry geometry;

    private String icon;

    private String name;

    private String place_id;

    private String rating;

    private String reference;

    private List<String> types;

    private int user_ratings_total;

    public void setBusiness_status(String business_status){
        this.business_status = business_status;
    }
    public String getBusiness_status(){
        return this.business_status;
    }
    public void setFormatted_address(String formatted_address){
        this.formatted_address = formatted_address;
    }
    public String getFormatted_address(){
        return this.formatted_address;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPlace_id(String place_id){
        this.place_id = place_id;
    }
    public String getPlace_id(){
        return this.place_id;
    }
    public void setRating(String rating){
        this.rating = rating;
    }
    public String getRating(){
        return this.rating;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    public String getReference(){
        return this.reference;
    }
    public void setTypes(List<String> types){
        this.types = types;
    }
    public List<String> getTypes(){
        return this.types;
    }
    public void setUser_ratings_total(int user_ratings_total){
        this.user_ratings_total = user_ratings_total;
    }
    public int getUser_ratings_total(){
        return this.user_ratings_total;
    }
    public class Geometry
    {
        private Location location;

        public void setLocation(Location location){
            this.location = location;
        }
        public Location getLocation(){
            return this.location;
        }

        public class Location
        {
            private double lat;

            private double lng;

            public void setLat(double lat){
                this.lat = lat;
            }
            public double getLat(){
                return this.lat;
            }
            public void setLng(double lng){
                this.lng = lng;
            }
            public double getLng(){
                return this.lng;
            }
            public LatLng getLatLng(){
                return new LatLng(lat, lng);
            }
        }
    }
}