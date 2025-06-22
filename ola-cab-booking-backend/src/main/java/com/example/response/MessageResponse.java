package com.example.response;

public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        super();
        this.message = message;
    }

    public MessageResponse(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
