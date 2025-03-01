package com.geoloc;

public class LocationData {
    private final double latitude;
    private final double longitude;
    private final String name;

    public LocationData(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}
