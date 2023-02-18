package com.hashmac.islam360.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Meta {
    @SerializedName("timezone")
    @Expose
    private String TimeZone;

    public Meta() {
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String timeZone) {
        TimeZone = timeZone;
    }
}
