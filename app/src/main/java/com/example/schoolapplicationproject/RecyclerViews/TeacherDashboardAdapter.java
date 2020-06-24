package com.example.schoolapplicationproject.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherDashboardRwLayoutBinding;

import java.util.ArrayList;

public class TeacherDashboardAdapter extends RecyclerView.Adapter<TeacherDashboardAdapter.MyViewHolder>{
    private ArrayList<TeacherDashboardLayout> arrayList;
    private OnItemSelected activity;

    public interface OnItemSelected {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemSelected listener){
        activity = listener;
    }

    public TeacherDashboardAdapter(Context context, ArrayList<TeacherDashboardLayout> arrayList){
        this.arrayList = arrayList;
        setOnItemClickListener((OnItemSelected) context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TeacherDashboardRwLayoutBinding binding;

        public MyViewHolder(TeacherDashboardRwLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClick(arrayList.indexOf((TeacherDashboardLayout) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public TeacherDashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new TeacherDashboardAdapter.MyViewHolder(TeacherDashboardRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(TeacherDashboardAdapter.MyViewHolder holder, int position){
        holder.itemView.setTag(arrayList.get(position));
        holder.binding.ivTeacherDashboardRwLayout.setImageResource(arrayList.get(position).getIcon());
        holder.binding.tvTeacherDashboardRwLayout.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }
}
