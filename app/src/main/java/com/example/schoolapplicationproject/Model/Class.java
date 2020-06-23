package com.example.schoolapplicationproject.Model;

import java.util.ArrayList;

public class Class {
    private String teacher;
    private String className;
    private String subject;
    private int year;
    private ArrayList<String> students;

    public Class() {
    }

    public Class(String teacher, String className, String subject, int year, ArrayList<String> students) {
        this.teacher = teacher;
        this.className = className;
        this.subject = subject;
        this.year = year;
        this.students = students;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }
}
