package com.example.loginandregisterusingretrofit.ModelClass;

public class DeleteModel {
    String Message,name;

    public DeleteModel(String message, String name) {
        Message = message;
        this.name = name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
