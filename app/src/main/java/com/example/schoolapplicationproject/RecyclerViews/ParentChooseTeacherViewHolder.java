package com.example.schoolapplicationproject.RecyclerViews;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.ParentChooseTeacherChatRvLayoutBinding;

public class ParentChooseTeacherViewHolder extends RecyclerView.ViewHolder {
    ParentChooseTeacherChatRvLayoutBinding binding;

    public ParentChooseTeacherViewHolder(ParentChooseTeacherChatRvLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

    }
}
