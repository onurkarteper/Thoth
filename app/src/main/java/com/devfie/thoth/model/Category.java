package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 8.04.2017.
 */

public class Category extends BaseObservable {

    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;

    @SerializedName("name")
    @Expose
    @Bindable
    private String name;

    @Bindable
    private Boolean check = false;


    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
