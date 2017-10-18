package com.accherniakocich.android.druzina.classes;

import java.io.Serializable;

public class Zaloba implements Serializable {
    private String character;
    private String adress;
    private String FIOZayavitelya;
    private String gotovPisatZayavlenie;
    private String opisanie;
    private String kontakti;
    private String obrabotana;

    public Zaloba() {
    }

    public Zaloba(String character, String adress, String FIOZayavitelya, String gotovPisatZayavlenie, String opisanie, String kontakti, String obrabotana) {
        this.character = character;
        this.adress = adress;
        this.FIOZayavitelya = FIOZayavitelya;
        this.gotovPisatZayavlenie = gotovPisatZayavlenie;
        this.opisanie = opisanie;
        this.kontakti = kontakti;
        this.obrabotana = obrabotana;
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

    public String getKontakti() {
        return kontakti;
    }

    public void setKontakti(String kontakti) {
        this.kontakti = kontakti;
    }

    public String getObrabotana() {
        return obrabotana;
    }

    public void setObrabotana(String obrabotana) {
        this.obrabotana = obrabotana;
    }
}
