package com.example.schoolapplicationproject.RecyclerViews;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherClassesAttendanceRwLayoutBinding;

public class TeacherClassViewHolder extends RecyclerView.ViewHolder {
    TeacherClassesAttendanceRwLayoutBinding binding;

    public TeacherClassViewHolder(TeacherClassesAttendanceRwLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }
}
