package com.accherniakocich.android.druzina.classes;

import java.io.Serializable;

public class Zaloba implements Serializable {
    private String character;
    private String adress;
    private String FIOZayavitelya;
    private String gotovPisatZayavlenie;
    private String opisanie;

    public Zaloba() {
    }

    public Zaloba(String character, String adress, String FIOZayavitelya, String gotovPisatZayavlenie, String opisanie) {
        this.character = character;
        this.adress = adress;
        this.FIOZayavitelya = FIOZayavitelya;
        this.gotovPisatZayavlenie = gotovPisatZayavlenie;
        this.opisanie = opisanie;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getFIOZayavitelya() {
        return FIOZayavitelya;
    }

    public void setFIOZayavitelya(String FIOZayavitelya) {
        this.FIOZayavitelya = FIOZayavitelya;
    }

    public String getGotovPisatZayavlenie() {
        return gotovPisatZayavlenie;
    }

    public void setGotovPisatZayavlenie(String gotovPisatZayavlenie) {
        this.gotovPisatZayavlenie = gotovPisatZayavlenie;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }
}
