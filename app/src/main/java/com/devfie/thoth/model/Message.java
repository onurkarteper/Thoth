package com.devfie.thoth.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mac-onur on 23.04.2017.
 */

public class Message extends BaseObservable {

    @SerializedName("_id")
    @Expose
    @Bindable
    private String id;

    @SerializedName("message")
    @Expose
    @Bindable
    private String message;

    @SerializedName("participants")
    @Expose
    @Bindable
    private ArrayList<String> participants = new ArrayList<>();

    @SerializedName("owner")
    @Expose
    @Bindable
    private User owner;

    @SerializedName("date")
    @Expose
    @Bindable
    private String date;

    @SerializedName("showDate")
    @Expose
    @Bindable
    private String showDate;

    public String getDate() {
        return date;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
