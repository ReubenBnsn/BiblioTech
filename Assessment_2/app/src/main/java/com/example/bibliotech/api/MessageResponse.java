package com.example.bibliotech.api;
import com.google.gson.annotations.SerializedName;

public class MessageResponse  {
    @SerializedName("message")
    private  String message;

    // for Gson
    public MessageResponse() {}

    // for Getter (lol forgetter)
    public String getMessage() {
        return message;
    }

    // for setter
    public void setMessage(String  message) {
        this.message = message;
    }
}
