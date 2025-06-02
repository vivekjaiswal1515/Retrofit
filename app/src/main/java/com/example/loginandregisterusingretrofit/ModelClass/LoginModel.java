package com.example.loginandregisterusingretrofit.ModelClass;

public class LoginModel {
    String emails,passwords,Message,ErrorCodes;

    public LoginModel(String emails, String passwords, String message, String errorCodes) {
        this.emails = emails;
        this.passwords = passwords;
        Message = message;
        ErrorCodes = errorCodes;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getErrorCodes() {
        return ErrorCodes;
    }

    public void setErrorCodes(String errorCodes) {
        ErrorCodes = errorCodes;
    }
}
