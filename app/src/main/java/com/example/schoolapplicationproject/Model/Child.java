package com.example.schoolapplicationproject.Model;

public class Child {
    private String student_key;
    private String name;

    @Override
    public String toString() {
        return "Student key: " + student_key + ", name: " + name;
    }
    public Child() {
    }

    public String getStudent_key() {
        return student_key;
    }

    public void setStudent_key(String student_key) {
        this.student_key = student_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Child(String student_key, String name) {
        this.student_key = student_key;
        this.name = name;
    }
}
