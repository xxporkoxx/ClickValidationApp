package com.example.diegomello.clickvalidationapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DiegoMello on 6/7/2017.
 */

public class Caretaker {

    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
