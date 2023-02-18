package com.hashmac.islam360.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Date {
    @SerializedName("hijri")
    @Expose
    private Hijri hijri;

    public Date() {
    }

    public Hijri getHijri() {
        return hijri;
    }

    public void setHijri(Hijri hijri) {
        this.hijri = hijri;
    }
}
