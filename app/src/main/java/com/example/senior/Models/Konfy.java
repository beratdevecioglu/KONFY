package com.example.senior.Models;

import com.google.firebase.database.ServerValue;

public class Konfy {


    private String konfyKey;
    private String baslik;
    private String aciklama;
    private String poster;
    private String kullanıcıAdı;
    private String kullanıcıAvatar;
    private Object zaman;

    public Konfy(String baslik, String aciklama, String poster, String kullanıcıAdı, String kullanıcıAvatar, Object zaman) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.poster = poster;
        this.kullanıcıAdı = kullanıcıAdı;
        this.kullanıcıAvatar = kullanıcıAvatar;
        this.zaman = ServerValue.TIMESTAMP;
    }

    public Konfy(String s, String toString, String imageDownlaodLink, String uid, String string) {
    }

    public String getKonfyKey() {
        return konfyKey;
    }

    public void setKonfyKey(String konfyKey) {
        this.konfyKey = konfyKey;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getPoster() {
        return poster;
    }

    public String getKullanıcıAdı() {
        return kullanıcıAdı;
    }

    public String getKullanıcıAvatar() {
        return kullanıcıAvatar;
    }

    public Object getZaman() {
        return zaman;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setKullanıcıAdı(String kullanıcıAdı) {
        this.kullanıcıAdı = kullanıcıAdı;
    }

    public void setKullanıcıAvatar(String kullanıcıAvatar) {
        this.kullanıcıAvatar = kullanıcıAvatar;
    }

    public void setZaman(Object zaman) {
        this.zaman = zaman;
    }
}
