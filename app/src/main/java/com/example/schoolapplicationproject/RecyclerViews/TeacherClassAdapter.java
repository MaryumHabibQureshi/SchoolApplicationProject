package com.example.schoolapplicationproject.RecyclerViews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schoolapplicationproject.databinding.TeacherClassesAttendanceRwLayoutBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TeacherClassAdapter extends FirestoreRecyclerAdapter<TeacherClassesLayout, TeacherClassAdapter.ClassHolder> {

    public TeacherClassAdapter(@NonNull FirestoreRecyclerOptions<TeacherClassesLayout> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassHolder holder, int position, @NonNull TeacherClassesLayout model) {
        holder.binding.tvTeacherClassesName.setText(model.getName());
        holder.binding.tvTeacherClassesSubject.setText(model.getSubject());
        int year = model.getYear();
        String sYear = Integer.toString(year);
        holder.binding.tvTeacherClassesYear.setText(sYear);
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassHolder(TeacherClassesAttendanceRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    public class ClassHolder extends RecyclerView.ViewHolder{
        TeacherClassesAttendanceRwLayoutBinding binding;
        public ClassHolder(TeacherClassesAttendanceRwLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
