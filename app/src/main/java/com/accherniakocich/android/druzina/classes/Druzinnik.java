package com.accherniakocich.android.druzina.classes;

import java.io.Serializable;

public class Druzinnik implements Serializable{
    private String FIO;
    private String rayon;
    private String marshrut;
    private String numberPhone;
    private String numberUdostoverenie;

    private double lat;
    private double lon;

    public Druzinnik() {
    }

    public Druzinnik(String FIO, String rayon, String marshrut, String numberPhone, String numberUdostoverenie, double lat, double lon) {
        this.FIO = FIO;
        this.rayon = rayon;
        this.marshrut = marshrut;
        this.numberPhone = numberPhone;
        this.numberUdostoverenie = numberUdostoverenie;
        this.lat = lat;
        this.lon = lon;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    public String getMarshrut() {
        return marshrut;
    }

    public void setMarshrut(String marshrut) {
        this.marshrut = marshrut;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getNumberUdostoverenie() {
        return numberUdostoverenie;
    }

    public void setNumberUdostoverenie(String numberUdostoverenie) {
        this.numberUdostoverenie = numberUdostoverenie;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
