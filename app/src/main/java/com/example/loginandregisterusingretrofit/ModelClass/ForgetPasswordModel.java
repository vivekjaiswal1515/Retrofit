package com.example.loginandregisterusingretrofit.ModelClass;

public class ForgetPasswordModel {
    String Message,email,password;

    public ForgetPasswordModel(String message, String email, String password) {
        Message = message;
        this.email = email;
        this.password = password;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
