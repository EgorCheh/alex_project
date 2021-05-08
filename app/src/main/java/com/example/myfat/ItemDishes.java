package com.example.myfat;

import android.net.Uri;

import java.net.URI;

public class ItemDishes {
    Uri image;
    String label;
    String belok;
    String jir;
    String uglevod;
    String kkl;

    public ItemDishes(Uri image, String label, String belok, String jir, String uglevod, String kkl) {
        this.image = image;
        this.label = label;
        this.belok = belok;
        this.jir = jir;
        this.uglevod = uglevod;
        this.kkl = kkl;
    }

    public ItemDishes() {
    }


    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBelok() {
        return belok;
    }

    public void setBelok(String belok) {
        this.belok = belok;
    }

    public String getJir() {
        return jir;
    }

    public void setJir(String jir) {
        this.jir = jir;
    }

    public String getUglevod() {
        return uglevod;
    }

    public void setUglevod(String uglevod) {
        this.uglevod = uglevod;
    }

    public String getKkl() {
        return kkl;
    }

    public void setKkl(String kkl) {
        this.kkl = kkl;
    }
}
