package com.example.zerrendasqllite;

import java.io.Serializable;
import java.util.List;

public class Lenguaia implements Serializable {

    private String izena;
    private String deskribapena;
    private boolean librea;

    public Lenguaia(String izena, String deskribapena, boolean librea) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.librea = librea;
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

    public boolean isLibrea() {
        return librea;
    }

    public void setLibrea(boolean librea) {
        this.librea = librea;
    }

    public int getID(Lenguaia lenguaia, List<Lenguaia> lenguaiak) {
        for (int i = 0; i < lenguaiak.size(); i++) {
            if (lenguaiak.get(i).equals(lenguaia)) {
                return i + 1;
            }
        }
        return 0;
    }
}
