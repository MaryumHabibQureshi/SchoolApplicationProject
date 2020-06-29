package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapplicationproject.R;
import com.example.schoolapplicationproject.RecyclerViews.ParentDashboardLayout;
import com.example.schoolapplicationproject.RecyclerViews.ParentDashboardAdapter;
import com.example.schoolapplicationproject.RecyclerViews.ParentDashboardLayout;
import com.example.schoolapplicationproject.databinding.FragmentParentDashboardBinding;
import com.example.schoolapplicationproject.databinding.FragmentParentDashboardBinding;

import java.util.ArrayList;


public class FragmentParentDashboard extends Fragment {
    private FragmentParentDashboardBinding binding;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<ParentDashboardLayout> parentDashboardLayoutArrayList;

    public FragmentParentDashboard() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentParentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parentDashboardLayoutArrayList = new ArrayList<>();
        addingParentDashboardLayout();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.parentDashboardRw.setLayoutManager(layoutManager);
        binding.parentDashboardRw.setHasFixedSize(true);
        myAdapter = new ParentDashboardAdapter(getContext(), parentDashboardLayoutArrayList);
        binding.parentDashboardRw.setAdapter(myAdapter);
    }

    public void notifyDataChanged() {
        myAdapter.notifyDataSetChanged();
    }

    private void addingParentDashboardLayout() {
        parentDashboardLayoutArrayList.add(new ParentDashboardLayout(R.drawable.ic_attendance, " View Attendance"));
        parentDashboardLayoutArrayList.add(new ParentDashboardLayout(R.drawable.ic_result, "View Result"));
        parentDashboardLayoutArrayList.add(new ParentDashboardLayout(R.drawable.ic_schedule, "Chat"));
        parentDashboardLayoutArrayList.add(new ParentDashboardLayout(R.drawable.ic_send_notification, "View Notification"));
    }
}

