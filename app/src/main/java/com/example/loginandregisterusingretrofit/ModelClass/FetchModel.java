package com.example.loginandregisterusingretrofit.ModelClass;

public class FetchModel {
  public   String name,email,password,gender,course,location,phone,Message,ErrorCodes;

    public FetchModel(String name, String email, String password, String gender, String course, String location, String phone, String message, String errorCodes) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.course = course;
        this.location = location;
        this.phone = phone;
        Message = message;
        ErrorCodes = errorCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
