package com.example.schoolapplicationproject;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.TeacherDashboardRwLayoutBinding;

import java.util.ArrayList;

public class TeacherDashboardAdapter extends RecyclerView.Adapter<TeacherDashboardAdapter.MyViewHolder>{
    private ArrayList<TeacherDashboardLayout> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TeacherDashboardRwLayoutBinding binding;

        public MyViewHolder(TeacherDashboardRwLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            
        }
    }

    public TeacherDashboardAdapter(ArrayList<TeacherDashboardLayout> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TeacherDashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new TeacherDashboardAdapter.MyViewHolder(TeacherDashboardRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(TeacherDashboardAdapter.MyViewHolder holder, int position){
        holder.binding.ivTeacherDashboardRwLayout.setImageResource(arrayList.get(position).getIcon());
        holder.binding.tvTeacherDashboardRwLayout.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }
}
