package com.example.schoolapplicationproject.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherClassesAttendanceRwLayoutBinding;
import java.util.ArrayList;

public class TeacherClassesAdapter extends RecyclerView.Adapter<TeacherClassesAdapter.MyViewHolder>{
    private ArrayList<TeacherClassesLayout> arrayList;
    private OnAttendanceItemSelected activity;

    public interface OnAttendanceItemSelected{
        void onAttendanceItemClick(int position);
    }

    public void setOnItemClickListener(OnAttendanceItemSelected listener){
        activity = listener;
    }

    public TeacherClassesAdapter(Context context, ArrayList<TeacherClassesLayout> arrayList){
        this.arrayList = arrayList;
        this.activity = (OnAttendanceItemSelected)context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TeacherClassesAttendanceRwLayoutBinding binding;
        public MyViewHolder(TeacherClassesAttendanceRwLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onAttendanceItemClick(arrayList.indexOf((TeacherClassesLayout) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public TeacherClassesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new TeacherClassesAdapter.MyViewHolder(TeacherClassesAttendanceRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }
    @Override
    public void onBindViewHolder(TeacherClassesAdapter.MyViewHolder holder, int position){
        holder.itemView.setTag(arrayList.get(position));
        holder.binding.tvTeacherClassesName.setText(arrayList.get(position).getName());
        holder.binding.tvTeacherClassesSubject.setText(arrayList.get(position).getSubject());
        holder.binding.tvTeacherClassesYear.setText(arrayList.get(position).getYear());
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }
}



