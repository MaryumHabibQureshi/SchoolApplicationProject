package com.example.schoolapplicationproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.schoolapplicationproject.Model.Child;
import com.example.schoolapplicationproject.Model.Parent;
import com.example.schoolapplicationproject.R;
import com.example.schoolapplicationproject.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentProfile extends Fragment {
    private FragmentProfileBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private String userId;
    private Parent parent;
    private static final String TAG = "FragmentProfile";
    final ArrayList<String> childArrayList = new ArrayList<>();


    public FragmentProfile() {
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseFirestore.setLoggingEnabled(true);
        userId = firebaseAuth.getCurrentUser().getUid();
        getParent();
        getChildren();


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
                setParent();
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

        binding.btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChildren();
                getChildren();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void getChildren() {
        database.collection("Parent").document(userId).collection("children").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            childArrayList.add(querySnapshot.getData() + "");
                        }
                        String string = "";
                        for (int i = 0; i < childArrayList.size(); i++) {
                            String str = childArrayList.get(i);
                            String result = str.substring(str.indexOf(",") + 7, str.indexOf("}"));
                            string = result + "\n" + string;
                        }
                        binding.tvChildName.setText(string);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "problem occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getParent() {
        database.collection("Parent").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
                parent = new Parent((documentSnapshot.getData().get("name")).toString(), contact, email, address, gender);
                binding.etName.setText(parent.getName());
                binding.etAddress.setText(parent.getAddress());
                binding.etContact.setText(parent.getContact());
                binding.etEmail.setText(parent.getEmail());
                binding.etGender.setText(parent.getGender());
            }
        });
    }

    public void setParent() {
        final String name = binding.etName.getText().toString().trim();
        final String email = binding.etEmail.getText().toString().trim();
        final String contact = binding.etContact.getText().toString().trim();
        final String address = binding.etAddress.getText().toString().trim();
        final String gender = binding.etGender.getText().toString().trim();

        DocumentReference documentReference = database.collection("Parent").document(userId);

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

    public void setChildren() {
        final ArrayList<Child> children = new ArrayList<>();
        if (binding.etChildren.getText().toString().trim().isEmpty()) {
            return;
        }
        final String student_key = binding.etChildren.getText().toString().trim();
        database.collection("Student")
                .whereEqualTo("student_key", student_key)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult() == null) {
                            Toast.makeText(getContext(), R.string.error_new_child, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            if (querySnapshot.getString("student_key").equals(student_key)) {
                                children.add(new Child(querySnapshot.getString("id"),
                                        querySnapshot.getString("fname") + " " + querySnapshot.getString("lname")));
                                Toast.makeText(getContext(), R.string.confirrm_child_added, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "working " + children.get(0).toString());
                            }
                        }
                        if (children.get(0) != null) {
                            DocumentReference df = database.collection("Student").document(children.get(0).getStudent_key());
                            Map<String, Object> p = new HashMap<>();
                            p.put("parent", userId);
                            Log.d(TAG, "working hr1");
                            df.set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "working hr3");
                                    Log.d(TAG, "onSuccess: Profile edit successfully.");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "working hr4");
                                    Log.d(TAG, "onFailure: Profile not edit.Error: " + e.toString());
                                }
                            });

                            Map<String, Object> user = new HashMap<>();
                            user.put("student_key", children.get(0).getStudent_key());
                            user.put("name", children.get(0).getName());
                            Log.d(TAG, "working hr2");

                            DocumentReference documentReference = database.collection("Parent")
                                    .document(userId)
                                    .collection("children").document();

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "working hr3");
                                    Log.d(TAG, "onSuccess: Profile edit successfully.");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "working hr4");
                                    Log.d(TAG, "onFailure: Profile not edit.Error: " + e.toString());
                                }
                            });
                        }
                    }
                });


    }
}