package com.example.schoolapplicationproject.RecyclerViews;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherAttendanceRwLayoutBinding;

public class TeacherStudentAttendanceViewHolder extends RecyclerView.ViewHolder {
    TeacherAttendanceRwLayoutBinding binding;

    public TeacherStudentAttendanceViewHolder(TeacherAttendanceRwLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
