package com.example.diegomello.clickvalidationapp;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by DiegoMello on 6/9/2017.
 */

public class Calling {
    @SerializedName("calltype")
    private Integer calltype;

    @SerializedName("updated")
    private Date updated;

    @SerializedName("callstatus")
    private Integer callstatus;

    @SerializedName("call_solved_at")
    private Date call_solved_at;


    public Integer getCalltype() {
        return calltype;
    }

    public void setCalltype(Integer calltype) {
        this.calltype = calltype;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getCallstatus() {
        return callstatus;
    }

    public void setCallstatus(Integer callstatus) {
        this.callstatus = callstatus;
    }

    public Date getCall_solved_at() {
        return call_solved_at;
    }

    public void setCall_solved_at(Date call_solved_at) {
        this.call_solved_at = call_solved_at;
    }
}
