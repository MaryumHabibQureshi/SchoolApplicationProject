package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.schoolapplicationproject.Fragments.FragmentAttendance;
import com.example.schoolapplicationproject.Fragments.FragmentTeacherDashboard;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardLayout;
import com.example.schoolapplicationproject.databinding.ActivityMainBinding;
import com.example.schoolapplicationproject.databinding.HeaderBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TeacherDashboardAdapter.OnItemSelected { //this class is teacher dashboard

    private ActivityMainBinding binding;
    private ArrayList<TeacherDashboardLayout> teacherDashboardLayoutArrayList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private static final String TAG = "login";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpToolbar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_frame_layout,new FragmentTeacherDashboard()).commit();

        userId = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = firebaseFirestore.collection("teacher").document(userId);

        binding.navigationView.bringToFront();
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
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
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





    }
    public void onItemClick(int index){
        switch (index){
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_frame_layout,new FragmentAttendance()).commit();
                Toast.makeText(this, "attend"+index, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "mark"+index, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "result"+index, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "meet"+index, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "notification"+index, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "default"+index, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void setUpToolbar(){
        setSupportActionBar(binding.teacherToolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.teacherToolbarMain,R.string.app_name,R.string.app_name);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}