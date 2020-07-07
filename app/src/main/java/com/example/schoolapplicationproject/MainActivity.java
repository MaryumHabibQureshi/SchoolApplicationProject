package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.schoolapplicationproject.Fragments.FragmentAttendance;
import com.example.schoolapplicationproject.Fragments.FragmentProfileTeacher;
import com.example.schoolapplicationproject.Fragments.FragmentTeacherDashboard;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassesAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherClassesLayout;
import com.example.schoolapplicationproject.RecyclerViews.TeacherDashboardAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherStudentAttendanceAdapter;
import com.example.schoolapplicationproject.RecyclerViews.TeacherStudentAttendanceLayout;
import com.example.schoolapplicationproject.databinding.ActivityMainBinding;
import com.example.schoolapplicationproject.databinding.HeaderBinding;
import com.example.schoolapplicationproject.databinding.TeacherAttendanceForClassDialogBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity
        extends AppCompatActivity
        implements TeacherDashboardAdapter.OnItemSelected,
        TeacherClassesAdapter.OnAttendanceItemSelected { //this class is teacher dashboard

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private static final String TAG = "teacherMain";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Dialog dialog;

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
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        binding.mainLogo.setVisibility(View.VISIBLE);
                        binding.teacherToolbarMain.setTitle(R.string.dashboard);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_frame_layout, new FragmentTeacherDashboard()).commit();
                        break;
                    case R.id.nav_attendance:
                        Toast.makeText(MainActivity.this, "attendance", Toast.LENGTH_SHORT).show();
                        binding.mainLogo.setVisibility(View.GONE);
                        binding.teacherToolbarMain.setTitle("Profile");
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_frame_layout, new FragmentAttendance()).commit();
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
                        binding.mainLogo.setVisibility(View.GONE);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_frame_layout, new FragmentProfileTeacher()).commit();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
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
        switch (index) {
            case 0:
                binding.teacherToolbarMain.setTitle(R.string.attendance);
                binding.mainLogo.setVisibility(View.GONE);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_frame_layout, new FragmentAttendance()).commit();
                Toast.makeText(this, "attend" + index, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "mark" + index, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "result" + index, Toast.LENGTH_SHORT).show();
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

    private void setUpToolbar() {
        setSupportActionBar(binding.teacherToolbarMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.teacherToolbarMain, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onAttendanceItemClick(int position, TeacherClassesLayout model) {
        final DocumentReference documentReference = firebaseFirestore.collection("Class").document(model.getClassId());
        DialogFragment dialogFragment = TeacherAttendanceFullScreenDialog.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("classname", model.getName());
        bundle.putString("classsubject", model.getSubject());
        bundle.putString("classid", model.getClassId());
        dialogFragment.setArguments(bundle);
        dialogFragment.show((MainActivity.this).getSupportFragmentManager(), TAG);

    }

    public static class TeacherAttendanceFullScreenDialog
            extends DialogFragment
            implements TeacherStudentAttendanceAdapter.OnStudentSelected {
        public TeacherAttendanceFullScreenDialog() {
        }

        private TeacherAttendanceForClassDialogBinding binding;
        private Date date;
        private ArrayList<TeacherStudentAttendanceLayout> arrayList;
        private ArrayList<String> attendanceId;
        private RecyclerView.Adapter myAdapter;
        private String classId;

        static TeacherAttendanceFullScreenDialog newInstance() {
            return new TeacherAttendanceFullScreenDialog();

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = TeacherAttendanceForClassDialogBinding.inflate(inflater, container, false);
            View view = binding.getRoot();

            Format formatter = new SimpleDateFormat("MMMM dd, yyyy");
            date = new Date();
            String dateString = formatter.format(date);
            binding.todayDate.setText(dateString);

            Bundle bundle = getArguments();
            binding.className.setText(bundle.getString("classname"));
            classId = bundle.getString("classid");

            arrayList = new ArrayList<>();
            attendanceId = new ArrayList<>();
            setData();

            MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Select Date for Attendance");

            final MaterialDatePicker materialDatePicker = builder.build();

            binding.buttonChangeDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDatePicker.show(getParentFragmentManager(), "Date_Picker");
                }
            });
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                @Override
                public void onPositiveButtonClick(Object selection) {
                    binding.todayDate.setText(materialDatePicker.getHeaderText());
                }
            });

            binding.btnFullscreenDialogClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public void onStudentItemClick(int position, TeacherStudentAttendanceLayout model) {

        }

        public void setData() {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.attendanceClassRv.setLayoutManager(layoutManager);
            binding.attendanceClassRv.setHasFixedSize(true);

            if (arrayList.size() > 0) {
                arrayList.clear();
            }
            if (attendanceId.size() > 0) {
                attendanceId.clear();
            }
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("Attendance")
                    .whereEqualTo("class", classId)
                    .whereEqualTo("date", date)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.getResult() != null) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    attendanceId.add(querySnapshot.getData() + "");
                                    Log.d(TAG, "working_a " + querySnapshot.getData());
                                }
                                String string = "";
                            } else {
                                //create new attendance
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "problem occurred.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}