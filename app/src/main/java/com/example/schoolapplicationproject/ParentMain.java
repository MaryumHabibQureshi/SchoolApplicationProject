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

import com.example.schoolapplicationproject.Fragments.FragmentParentDashboard;
import com.example.schoolapplicationproject.Fragments.FragmentProfile;
import com.example.schoolapplicationproject.Fragments.FragmentTeacherDashboard;
import com.example.schoolapplicationproject.RecyclerViews.ParentDashboardAdapter;
import com.example.schoolapplicationproject.databinding.ActivityMainBinding;
import com.example.schoolapplicationproject.databinding.ActivityParentMainBinding;
import com.example.schoolapplicationproject.databinding.HeaderBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ParentMain extends AppCompatActivity implements ParentDashboardAdapter.OnItemSelected {

    private ActivityParentMainBinding binding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private static final String TAG = "parentMain";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParentMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpToolbar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_frame_layout_parent, new FragmentParentDashboard()).commit();

        userId = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = firebaseFirestore.collection("Parent").document(userId);

        binding.navigationView.bringToFront();
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home_p:
                        binding.mainLogo.setVisibility(View.VISIBLE);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_frame_layout_parent, new FragmentParentDashboard()).commit();
                        Toast.makeText(ParentMain.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_attendance_p:
                        Toast.makeText(ParentMain.this, R.string.attendance_report, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_result_p:
                        Toast.makeText(ParentMain.this, R.string.results_report, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_notification_p:
                        Toast.makeText(ParentMain.this, R.string.notification, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile_p:
                        binding.mainLogo.setVisibility(View.GONE);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_frame_layout_parent, new FragmentProfile()).commit();
                        Toast.makeText(ParentMain.this, R.string.profile, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout_p:
                        Toast.makeText(ParentMain.this, R.string.logout, Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
                    if (documentSnapshot.getData().get("gender") != null) {
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("gender")).toString());
                    }
                    if (documentSnapshot.getData().get("address") != null) {
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("address")).toString());
                    }
                    if (documentSnapshot.getData().get("contact") != null) {
                        headerBinding.tvGender.setText((documentSnapshot.getData().get("contact")).toString());
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


    }

    private void setUpToolbar() {
        setSupportActionBar(binding.teacherToolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.teacherToolbarMain, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "default" + position, Toast.LENGTH_SHORT).show();
    }
}