package com.example.mapapp;

import com.google.android.gms.maps.model.LatLng;

public class Marker {
    String id;
    String title;
    LatLng position;

    public Marker() {
    }

    public Marker(String id, String title, LatLng position) {
        this.id = id;
        this.title = title;
        this.position = position;
    }

    public Marker(String title, LatLng position) {
        this.title = title;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
