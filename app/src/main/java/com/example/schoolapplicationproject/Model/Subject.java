package com.example.schoolapplicationproject.Model;

public class Subject {
    private String name;
    private String type;

    public Subject(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
