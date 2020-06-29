package com.example.schoolapplicationproject.RecyclerViews;

public class TeacherClassesLayout {


    private String classId;
    private String name;
    private String subject;
    private int year;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public TeacherClassesLayout(String classId, String name, String subject, int year) {
        this.classId = classId;
        this.name = name;
        this.subject = subject;
        this.year = year;
    }

    public TeacherClassesLayout() {
    }
}
