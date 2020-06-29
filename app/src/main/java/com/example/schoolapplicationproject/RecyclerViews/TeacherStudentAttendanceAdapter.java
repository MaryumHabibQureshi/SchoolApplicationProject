package com.example.schoolapplicationproject.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherAttendanceRwLayoutBinding;

import java.util.ArrayList;

public class TeacherStudentAttendanceAdapter extends RecyclerView.Adapter<TeacherStudentAttendanceViewHolder> {
    private ArrayList<TeacherStudentAttendanceLayout> arrayList;
    private OnStudentSelected activity;

    @NonNull
    @Override
    public TeacherStudentAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherStudentAttendanceViewHolder(TeacherAttendanceRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherStudentAttendanceViewHolder holder, int position) {
        holder.itemView.setTag(arrayList.get(position));
        holder.binding.tvStudentName.setText(arrayList.get(position).getName());
        holder.binding.cbStudentStatus.setSelected(arrayList.get(position).isStatus());
        holder.binding.cbStudentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = arrayList.indexOf(v.getTag());
                activity.onStudentItemClick(index, arrayList.get(index));
            }
        });
    }

    public void setOnItemClickListener(TeacherStudentAttendanceAdapter.OnStudentSelected listener) {
        activity = listener;
    }

    public interface OnStudentSelected {
        void onStudentItemClick(int position, TeacherStudentAttendanceLayout model);
    }

    public TeacherStudentAttendanceAdapter(Context context, ArrayList<TeacherStudentAttendanceLayout> arrayList) {
        this.arrayList = arrayList;
        setOnItemClickListener((TeacherStudentAttendanceAdapter.OnStudentSelected) context);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
