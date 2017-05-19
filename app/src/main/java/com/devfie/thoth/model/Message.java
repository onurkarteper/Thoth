package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-onur on 23.04.2017.
 */

public class Message extends BaseObservable {

    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;


    @SerializedName("type")
    @Expose
    @Bindable
    private Integer type;

    @SerializedName("content")
    @Expose
    @Bindable
    private String content;

    @SerializedName("to")
    @Expose
    @Bindable
    private String to;

    @SerializedName("from")
    @Expose
    @Bindable
    private User owner;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
