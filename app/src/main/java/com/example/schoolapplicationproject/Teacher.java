package com.example.schoolapplicationproject;

public class Teacher {
    private String name;
    private String email;
    private String gender;
    private String contact;
    private String address;

    public Teacher(String name, String email, String gender, String contact, String address) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
    }

    public Teacher() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
