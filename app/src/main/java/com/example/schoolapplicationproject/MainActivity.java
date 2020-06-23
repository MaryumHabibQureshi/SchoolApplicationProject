package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.schoolapplicationproject.databinding.ActivityMainBinding;
import com.example.schoolapplicationproject.databinding.HeaderBinding;
import com.example.schoolapplicationproject.databinding.TeacherDashboardRwLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity { //this class is teacher dashboard

    private ActivityMainBinding binding;
    private ArrayList<TeacherDashboardLayout> teacherDashboardLayoutArrayList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpToolbar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = firebaseFirestore.collection("teacher").document(userId);

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_attendance:
                        Toast.makeText(MainActivity.this, "attendance", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_test_marks:
                        Toast.makeText(MainActivity.this, "test marks", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_result:
                        Toast.makeText(MainActivity.this, "results", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_notification:
                        Toast.makeText(MainActivity.this, "notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile:
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });

        View headerView = binding.navigationView.getHeaderView(0);
        final HeaderBinding headerBinding = HeaderBinding.bind(headerView);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    //Log.d(TAG, "Current data: " + documentSnapshot.getData().get("email"));
                    documentSnapshot.getData().get("name");
                    headerBinding.tvName.setText((documentSnapshot.getData().get("name")).toString());
                    headerBinding.tvEmail.setText((documentSnapshot.getData().get("email")).toString());
                    if(documentSnapshot.getData().get("gender") != null){
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("gender")).toString());
                    }
                    if(documentSnapshot.getData().get("address") != null){
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("address")).toString());
                    }
                    if(documentSnapshot.getData().get("contact") != null){
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("contact")).toString());
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        teacherDashboardLayoutArrayList = new ArrayList<>();
        addingTeacherDashboardLayout();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.teacherDashboardRw.setLayoutManager(layoutManager);
        binding.teacherDashboardRw.setAdapter(new TeacherDashboardAdapter(teacherDashboardLayoutArrayList));


    }
    private void addingTeacherDashboardLayout(){
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_attendance," Attendance"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_test_marks,"Upload Marks of Test"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_result,"Upload Result"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_schedule,"Schedule a Meeting"));
        teacherDashboardLayoutArrayList.add(new TeacherDashboardLayout(R.drawable.ic_send_notification,"Send a Notification"));
    }
    private void setUpToolbar(){
        setSupportActionBar(binding.teacherToolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.teacherToolbarMain,R.string.app_name,R.string.app_name);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}