package com.example.senior.Models;


public class Konfy {String FDbaslik, FDkategori, FDkonfylink, FDkonfyaciklama;

    public Konfy() {
    }

    public Konfy(String FDbaslik, String FDkategori, String FDkonfylink, String FDkonfyaciklama) {
        this.FDbaslik = FDbaslik;
        this.FDkategori = FDkategori;
        this.FDkonfylink = FDkonfylink;
        this.FDkonfyaciklama = FDkonfyaciklama;
    }

    public String getFDbaslik() {
        return FDbaslik;
    }

    public void setFDbaslik(String FDbaslik) {
        this.FDbaslik = FDbaslik;
    }

    public String getFDkategori() {
        return FDkategori;
    }

    public void setFDkategori(String FDkategori) {
        this.FDkategori = FDkategori;
    }

    public String getFDkonfylink() {
        return FDkonfylink;
    }

    public void setFDkonfylink(String FDkonfylink) {
        this.FDkonfylink = FDkonfylink;
    }

    public String getFDkonfyaciklama() {
        return FDkonfyaciklama;
    }

    public void setFDkonfyaciklama(String FDkonfyaciklama) {
        this.FDkonfyaciklama = FDkonfyaciklama;
    }
}
