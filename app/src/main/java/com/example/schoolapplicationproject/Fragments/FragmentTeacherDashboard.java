package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.R;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardLayout;
import com.example.schoolapplicationproject.databinding.FragmentTeacherDashboardBinding;

import java.util.ArrayList;

public class FragmentTeacherDashboard extends Fragment {
    private FragmentTeacherDashboardBinding binding;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<TeacherDashboardLayout>teacherDashboardLayoutArrayList;

    public FragmentTeacherDashboard(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeacherDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teacherDashboardLayoutArrayList = new ArrayList<>();
        addingTeacherDashboardLayout();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.teacherDashboardRw.setLayoutManager(layoutManager);
        binding.teacherDashboardRw.setHasFixedSize(true);
        myAdapter = new TeacherDashboardAdapter(getContext(),teacherDashboardLayoutArrayList);
        binding.teacherDashboardRw.setAdapter(myAdapter);
    }
    public void notifyDataChanged(){
        myAdapter.notifyDataSetChanged();
    }

    private void addingTeacherDashboardLayout(){
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_attendance," Attendance"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_test_marks,"Upload Marks of Test"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_result,"Upload Result"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_schedule,"Schedule a Meeting"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_send_notification,"Send a Notification"));
    }
}
