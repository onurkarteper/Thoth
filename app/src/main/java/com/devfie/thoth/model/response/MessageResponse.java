package com.devfie.thoth.model.response;

import com.devfie.thoth.model.Message;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by on 11.06.2017.
 */

public class MessageResponse extends BaseResponse {

    @SerializedName("response")
    @Expose
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
