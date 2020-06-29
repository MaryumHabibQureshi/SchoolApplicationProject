package com.example.schoolapplicationproject.RecyclerViews;

public class TeacherStudentAttendanceLayout {
    private String name;
    private boolean status;

    public TeacherStudentAttendanceLayout(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public TeacherStudentAttendanceLayout() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
