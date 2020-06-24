package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.Model.Teacher;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassesLayout;
import com.example.schoolapplicationproject.databinding.FragmentAttendanceBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentAttendance extends Fragment {

    private FragmentAttendanceBinding binding;
    private TeacherClassAdapter adapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private String userId;
    private static final String TAG = "FragmentAttendance";
    private CollectionReference classRef = database.collection("Class");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public FragmentAttendance(){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        userId = firebaseAuth.getCurrentUser().getUid();
        setUpRecyclerView();
    }
    public void setUpRecyclerView(){
        Query query = classRef
                .whereEqualTo("teacher",userId)
                .orderBy("year",Query.Direction.DESCENDING);

        addingTeacherClasses();

        FirestoreRecyclerOptions<TeacherClassesLayout> options = new FirestoreRecyclerOptions.Builder<TeacherClassesLayout>()
                .setQuery(query, new SnapshotParser<TeacherClassesLayout>() {
                    @NonNull
                    @Override
                    public TeacherClassesLayout parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        TeacherClassesLayout model = new TeacherClassesLayout();
                        if(snapshot.get("name") != null){
                            model.setName(snapshot.get("name").toString());
                        }else{
                            model.setName("name");
                        }
                        if(snapshot.get("subject") != null){
                            model.setSubject(snapshot.get("subject").toString());
                        }else{
                            model.setSubject("subject");
                        }
                        if(snapshot.get("year") != null){
                            model.setYear(Integer.parseInt(snapshot.get("year").toString()));
                        }else{
                            model.setYear(1900);
                        }
                        return model;
                    }
                }).build();
        adapter = new TeacherClassAdapter(options);

        binding.rvTeacherClassList.setHasFixedSize(true);
        binding.rvTeacherClassList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTeacherClassList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void addingTeacherClasses(){
        database.collection("Class")
                .whereEqualTo("teacher",userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Log.d(TAG, "important: "+documentSnapshot.getData());
                        }


                    }
                });

    }
}
