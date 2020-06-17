package com.example.schoolapplicationproject;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    static ArrayList<TeacherDashboardLayout> teacherDashboardLayoutArrayList;

    public void onCreate(){
        super.onCreate();

        teacherDashboardLayoutArrayList = new ArrayList<>();
        addingTeacherDashboardLayout();
    }

    public void addingTeacherDashboardLayout(){
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_attendance,"Upload Attendance"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_test_marks,"Upload Marks of Test"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_result,"Upload Result"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_schedule,"Schedule a Meeting"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_send_notification,"Send a Notification"));
    }

}
