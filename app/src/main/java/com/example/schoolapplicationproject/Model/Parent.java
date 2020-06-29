package com.example.schoolapplicationproject.Model;

public class Parent {
    private String name;
    private String contact;
    private String email;
    private String address;
    private String gender;

    public Parent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Parent(String name, String contact, String email, String address, String gender) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }
}
