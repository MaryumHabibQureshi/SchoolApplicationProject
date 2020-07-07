package com.example.schoolapplicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.schoolapplicationproject.RecyclerViews.ParentChooseTeacherAdapter;
import com.example.schoolapplicationproject.RecyclerViews.ParentChooseTeacherLayout;
import com.example.schoolapplicationproject.databinding.ActivityChooseTeacherToChatWithBinding;
import com.example.schoolapplicationproject.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class ChooseTeacherToChatWithActivity extends AppCompatActivity
        implements ParentChooseTeacherAdapter.OnChooseTeacherItemSelected {

    private ActivityChooseTeacherToChatWithBinding binding;
    private ArrayList<ParentChooseTeacherLayout> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseTeacherToChatWithBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.tbChooseTeacher);

        arrayList = new ArrayList<>();


    }

    @Override
    public void onChooseTeacherItemClick(int position, ParentChooseTeacherLayout model) {

    }
}