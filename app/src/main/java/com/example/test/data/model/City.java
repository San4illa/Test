package com.example.test.data.model;

public class City {

    private String country;
    private String name;
    private double lat;
    private double lng;

    // City("RU", "Saint Petersburg", 59.9402, 30.3138)
    public City(String country, String name, double lat, double lng) {
        this.country = country;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
