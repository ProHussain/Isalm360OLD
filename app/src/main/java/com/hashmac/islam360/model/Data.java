package com.hashmac.islam360.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Data {
    @SerializedName("timings")
    @Expose
    private Timings timings;

    @SerializedName("date")
    @Expose
    private Date date;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Data() {
    }

    public Timings getTimings() {
        return timings;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimings(Timings timings) {
        this.timings = timings;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}

