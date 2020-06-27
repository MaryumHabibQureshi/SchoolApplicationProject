package com.example.schoolapplicationproject.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.databinding.ParentDashboardRwLayoutBinding;

import java.util.ArrayList;

public class ParentDashboardAdapter extends RecyclerView.Adapter<ParentDashboardAdapter.MyViewHolder>{
    private ArrayList<ParentDashboardLayout> arrayList;
    private ParentDashboardAdapter.OnItemSelected activity;

    public interface OnItemSelected {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ParentDashboardAdapter.OnItemSelected listener){
        activity = listener;
    }

    public ParentDashboardAdapter(Context context, ArrayList<ParentDashboardLayout> arrayList){
        this.arrayList = arrayList;
        setOnItemClickListener((ParentDashboardAdapter.OnItemSelected) context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ParentDashboardRwLayoutBinding binding;

        public MyViewHolder(ParentDashboardRwLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClick(arrayList.indexOf((ParentDashboardLayout) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ParentDashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ParentDashboardAdapter.MyViewHolder(ParentDashboardRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ParentDashboardAdapter.MyViewHolder holder, int position){
        holder.itemView.setTag(arrayList.get(position));
        holder.binding.ivParentDashboardRwLayout.setImageResource(arrayList.get(position).getIcon());
        holder.binding.tvParentDashboardRwLayout.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }
}
