package com.hashmac.islam360.model;

import androidx.annotation.Keep;

@Keep
public class Reponse {
    private int code;
    private String status;
    private Qibla data;

    public Reponse() {
    }

    public Reponse(int code, String status, Qibla data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Qibla getData() {
        return data;
    }

    public void setData(Qibla data) {
        this.data = data;
    }
}
