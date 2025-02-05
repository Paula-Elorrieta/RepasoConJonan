package com.example.zerrendaerrepasoa;

import java.io.Serializable;

public class Lekua implements Serializable {
    private long id;
    private String izena;
    private String deskribapena;
    private double latitude;
    private double longitude;


    public Lekua(long id, String izena, String deskribapena, double latitude, double longitude) {
        this.id = id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Lekua() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
