package ariketa2;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String erabiltzailea;
    private String izena;
    private String pasahitza;

    public Usuario(String erabiltzailea, String izena, String pasahitza) {
        this.erabiltzailea = erabiltzailea;
        this.izena = izena;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzailea() {
        return erabiltzailea;
    }

    public String getIzena() {
        return izena;
    }

    public String getPasahitza() {
        return pasahitza;
    }
}
