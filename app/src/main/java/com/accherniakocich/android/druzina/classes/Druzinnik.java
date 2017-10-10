package com.accherniakocich.android.druzina.classes;

import java.io.Serializable;

public class Druzinnik implements Serializable{
    private String FIO;
    private String rayon;
    private String marshrut;
    private String numberPhone;
    private String numberUdostoverenie;

    public Druzinnik() {
    }

    public Druzinnik(String FIO, String rayon, String marshrut, String numberPhone, String numberUdostoverenie) {
        this.FIO = FIO;
        this.rayon = rayon;
        this.marshrut = marshrut;
        this.numberPhone = numberPhone;
        this.numberUdostoverenie = numberUdostoverenie;
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
}
