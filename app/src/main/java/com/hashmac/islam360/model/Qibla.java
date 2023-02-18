package com.hashmac.islam360.model;

import androidx.annotation.Keep;

@Keep
public class Qibla {
    private double longitude;
    private double latitude;
    private double direction;

    public Qibla() {
    }

    public Qibla(double longitude, double latitude, double direction) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.direction = direction;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}
