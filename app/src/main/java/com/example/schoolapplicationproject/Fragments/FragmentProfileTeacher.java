package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolapplicationproject.Model.Parent;
import com.example.schoolapplicationproject.Model.Teacher;
import com.example.schoolapplicationproject.databinding.FragmentProfileTeacherBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class FragmentProfileTeacher extends Fragment {
    private FragmentProfileTeacherBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private String userId;
    private Teacher teacher;
    private static final String TAG = "FragmentProfileTeacher";

    public FragmentProfileTeacher() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileTeacherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseFirestore.setLoggingEnabled(true);
        userId = firebaseAuth.getCurrentUser().getUid();

        getTeacher();

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etName.setEnabled(true);
                binding.etName.setInputType(InputType.TYPE_CLASS_TEXT);

                binding.etAddress.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etAddress.setEnabled(true);

                binding.etContact.setInputType(InputType.TYPE_CLASS_PHONE);
                binding.etContact.setEnabled(true);

                binding.etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etEmail.setEnabled(true);

                binding.etGender.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etGender.setEnabled(true);

                binding.btnProfile.setVisibility(View.GONE);
                binding.btnSave.setVisibility(View.VISIBLE);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeacher();
                binding.etName.setEnabled(false);
                binding.etName.setInputType(InputType.TYPE_CLASS_TEXT);

                binding.etAddress.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etAddress.setEnabled(false);

                binding.etContact.setInputType(InputType.TYPE_CLASS_PHONE);
                binding.etContact.setEnabled(false);

                binding.etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etEmail.setEnabled(false);

                binding.etGender.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.etGender.setEnabled(false);

                binding.btnProfile.setVisibility(View.VISIBLE);
                binding.btnSave.setVisibility(View.GONE);
            }
        });
    }

    public void getTeacher() {
        database.collection("teacher").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String contact = "";
                if (documentSnapshot.getData().get("contact") != null) {
                    contact = (documentSnapshot.getData().get("contact")).toString();
                }
                String email = "";
                if (documentSnapshot.getData().get("email") != null) {
                    email = (documentSnapshot.getData().get("email")).toString();
                }
                String address = "";
                if (documentSnapshot.getData().get("address") != null) {
                    address = (documentSnapshot.getData().get("address")).toString();
                }
                String gender = "";
                if (documentSnapshot.getData().get("gender") != null) {
                    gender = (documentSnapshot.getData().get("gender")).toString();
                }
                teacher = new Teacher((documentSnapshot.getData().get("name")).toString(), email, gender, contact, address);
                binding.etName.setText(teacher.getName());
                binding.etAddress.setText(teacher.getAddress());
                binding.etContact.setText(teacher.getContact());
                binding.etEmail.setText(teacher.getEmail());
                binding.etGender.setText(teacher.getGender());
            }
        });
    }

    public void setTeacher() {
        final String name = binding.etName.getText().toString().trim();
        final String email = binding.etEmail.getText().toString().trim();
        final String contact = binding.etContact.getText().toString().trim();
        final String address = binding.etAddress.getText().toString().trim();
        final String gender = binding.etGender.getText().toString().trim();

        DocumentReference documentReference = database.collection("teacher").document(userId);

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("contact", contact);
        user.put("address", address);
        user.put("gender", gender);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Profile edit successfully.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Profile not edit.Error: " + e.toString());
            }
        });
    }
}
