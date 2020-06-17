package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapplicationproject.databinding.ActivityMainBinding;
import com.example.schoolapplicationproject.databinding.TeacherDashboardRwLayoutBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.teacherDashboardRw.setLayoutManager(layoutManager);
        binding.teacherDashboardRw.setAdapter(new TeacherDashboardAdapter(MyApplication.teacherDashboardLayoutArrayList));
    }
}