package com.example.senior.HomeAdapter;

public class GuncelHelperClass {


    int image;
    String baslik, aciklama;

    public GuncelHelperClass(int image, String baslik, String aciklama) {
        this.image = image;
        this.baslik = baslik;
        this.aciklama = aciklama;
    }

    public int getImage() {
        return image;
    }

    public String getBaslik() {
        return baslik;
    }

    public String getAciklama() {
        return aciklama;
    }
}

