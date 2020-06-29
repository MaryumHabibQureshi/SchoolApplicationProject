package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.Model.Teacher;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassesAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassesLayout;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardAdapter;
import com.example.schoolapplicationproject.databinding.FragmentAttendanceBinding;
import com.example.schoolapplicationproject.databinding.TeacherClassesAttendanceRwLayoutBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    //private TeacherClassAdapter adapter;
//    FirestoreRecyclerAdapter<TeacherClassesLayout, ClassHolder> adapter;
    private String userId;
    private static final String TAG = "FragmentAttendance";

    private ArrayList<TeacherClassesLayout> arrayList;
    private RecyclerView.Adapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public FragmentAttendance() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        userId = firebaseAuth.getCurrentUser().getUid();


//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        binding.rvTeacherClassList.setLayoutManager(layoutManager);
//        binding.rvTeacherClassList.setHasFixedSize(true);
//        setUpRecyclerView();

        arrayList = new ArrayList<>();
        loadData();
//        addingTeacherClasses();
//        arrayList.add(new TeacherClassesLayout("a","a",2));
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        binding.rvTeacherClassList.setLayoutManager(layoutManager);
//        binding.rvTeacherClassList.setHasFixedSize(true);
//        myAdapter = new TeacherClassesAdapter(getContext(),arrayList);
//        binding.rvTeacherClassList.setAdapter(myAdapter);
    }

    public void loadData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvTeacherClassList.setHasFixedSize(true);
        binding.rvTeacherClassList.setLayoutManager(layoutManager);

        if (arrayList.size() > 0) {
            arrayList.clear();
        }

        database.collection("Class")
                .whereEqualTo("teacher", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            TeacherClassesLayout model = new TeacherClassesLayout(querySnapshot.getString("id"), querySnapshot.getString("name"), querySnapshot.getString("subject"), Integer.parseInt(querySnapshot.get("year").toString()));
                            arrayList.add(model);
                        }
                        myAdapter = new TeacherClassesAdapter(getContext(), arrayList);
                        binding.rvTeacherClassList.setAdapter(myAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "problem occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void notifyDataChanged() {
        myAdapter.notifyDataSetChanged();
    }

    public String getSubjectName(String subjectKey) {
        final String[] subject = {""};
        database.collection("Subject").document(subjectKey).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.d(TAG, "onComplete: " + task.getResult());
                        if (task.getResult().getData().get("name") != null) {
                            subject[0] = task.getResult().getData().get("name").toString();
                        } else {
                            subject[0] = "";
                        }
                    }
                });

        return subject[0];
    }

//    public void addingTeacherClasses(){
//        database.collection("Class")
//                .whereEqualTo("teacher",userId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (DocumentSnapshot documentSnapshot: task.getResult()){
//                                Log.d(TAG, "important: "+documentSnapshot.getData());
//                                TeacherClassesLayout obj = new TeacherClassesLayout();
//                                String name = (documentSnapshot.getData().get("name") != null)?documentSnapshot.getData().get("name").toString():"name";
//                                obj.setName(name);
//                                String subject = (documentSnapshot.getData().get("subject") != null)?documentSnapshot.getData().get("subject").toString():"subject";
//                                obj.setSubject(subject);
//                                int year = (documentSnapshot.getData().get("year") != null)?Integer.parseInt(documentSnapshot.getData().get("year").toString()):1900;
//                                obj.setYear(year);
//                                arrayList.add(obj);
//                            }
//                        }
//                    }
//                });
//
//    }
//    public void setUpRecyclerView(){
//        Query query = database.collection("Class")
//                .orderBy("name").limit(30);;
//
//        FirestoreRecyclerOptions<TeacherClassesLayout> options = new FirestoreRecyclerOptions.Builder<TeacherClassesLayout>()
//                .setQuery(query, new SnapshotParser<TeacherClassesLayout>() {
//                    @NonNull
//                    @Override
//                    public TeacherClassesLayout parseSnapshot(@NonNull DocumentSnapshot snapshot) {
//                        TeacherClassesLayout model = new TeacherClassesLayout();
//                        if(snapshot.get("name") != null){
//                            model.setName(snapshot.get("name").toString());
//                        }else{
//                            model.setName("name");
//                        }
//                        if(snapshot.get("subject") != null){
//                            model.setSubject(snapshot.get("subject").toString());
//                        }else{
//                            model.setSubject("subject");
//                        }
//                        if(snapshot.get("year") != null){
//                            model.setYear(Integer.parseInt(snapshot.get("year").toString()));
//                        }else{
//                            model.setYear(1900);
//                        }
//                        return model;
//                    }
//                }).build();
//
////        adapter = new FirestoreRecyclerAdapter<TeacherClassesLayout, ClassHolder>(options) {
////            @Override
////            protected void onBindViewHolder(@NonNull ClassHolder holder, int position, @NonNull TeacherClassesLayout model) {
////                holder.binding.tvTeacherClassesName.setText(model.getName());
////                holder.binding.tvTeacherClassesSubject.setText(model.getSubject());
////                int year = model.getYear();
////                String sYear = Integer.toString(year);
////                holder.binding.tvTeacherClassesYear.setText(sYear);
////            }
////
////            @NonNull
////            @Override
////            public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////                return new ClassHolder(TeacherClassesAttendanceRwLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
////                        parent, false));
////            }
////        };
//
//        //adapter = new TeacherClassAdapter(options);
//
//        //binding.rvTeacherClassList.setHasFixedSize(true);
//        binding.rvTeacherClassList.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.rvTeacherClassList.setAdapter(adapter);
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }


}
//class ClassHolder extends RecyclerView.ViewHolder{
//    TeacherClassesAttendanceRwLayoutBinding binding;
//    public ClassHolder(TeacherClassesAttendanceRwLayoutBinding binding) {
//        super(binding.getRoot());
//        this.binding = binding;
//    }
//    public void setLayout(String name, String subject, int year){
//        binding.tvTeacherClassesName.setText(name);
//        binding.tvTeacherClassesSubject.setText(subject);
//        binding.tvTeacherClassesYear.setText(Integer.toString(year));
//    }
//}
