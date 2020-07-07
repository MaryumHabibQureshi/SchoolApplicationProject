package com.example.schoolapplicationproject.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.ParentChooseTeacherChatRvLayoutBinding;

import java.util.ArrayList;

public class ParentChooseTeacherAdapter extends RecyclerView.Adapter<ParentChooseTeacherViewHolder> {

    private ArrayList<ParentChooseTeacherLayout> arrayList;
    private OnChooseTeacherItemSelected activity;

    @NonNull
    @Override
    public ParentChooseTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ParentChooseTeacherViewHolder(ParentChooseTeacherChatRvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentChooseTeacherViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(position));
        holder.binding.tvTeacherName.setText(arrayList.get(position).getName());
        holder.binding.tvTeacherEmail.setText(arrayList.get(position).getEmail());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = arrayList.indexOf((TeacherClassesLayout) v.getTag());
                activity.onChooseTeacherItemClick(index, arrayList.get(index));
            }
        });
    }

    public interface OnChooseTeacherItemSelected {
        void onChooseTeacherItemClick(int position, ParentChooseTeacherLayout model);
    }

    public void setOnItemClickListener(OnChooseTeacherItemSelected listener) {
        activity = listener;
    }

    public ParentChooseTeacherAdapter(Context context, ParentChooseTeacherLayout model) {
        this.arrayList = arrayList;
        setOnItemClickListener((OnChooseTeacherItemSelected) context);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
