package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.schoolapplicationproject.Model.Users;
import com.example.schoolapplicationproject.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = binding.tfName.getText().toString().trim();
                final String email = binding.tfEmail.getText().toString().trim();
                String password = binding.tfPassword.getText().toString().trim();
                int radioButton = binding.radioGroupType.getCheckedRadioButtonId();
                RadioButton radioBtn = findViewById(radioButton);
                final String type = radioBtn.getText().toString();

                if(name.isEmpty()){
                    binding.tfName.setError("Name is required.");
                    return;
                }
                if(email.isEmpty()){
                    binding.tfEmail.setError("Email is required.");
                    return;
                }
                if(password.isEmpty()){
                    binding.tfPassword.setError("Password is required.");
                    return;
                }
                if(password.length() < 6){
                    binding.tfPassword.setError("Password must have minimum 6 characters.");
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();


                            DocumentReference documentReferenceUser = firebaseFirestore.collection("Users").document(userId);
                            Users userType = new Users(email,type);

                            try {
                                firebaseFirestore.collection("Users")
                                        .document(userId)
                                        .set(userType)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: User created.");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: User not created.Error: "+e.toString());
                                            }
                                        });
                            }catch (Exception e){
                                Log.d(TAG, "onComplete: "+e.getMessage());
                            }

                            DocumentReference documentReference;
                            if(type.equals("Teacher")){
                                documentReference = firebaseFirestore.collection("teacher").document(userId);
                            }
                            else{
                                documentReference = firebaseFirestore.collection("Parent").document(userId);
                            }
                            Map <String,Object> user = new HashMap<>();
                            user.put("name",name);
                            user.put("email",email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User created.");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: User not created.Error: "+e.toString());
                                }
                            });

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{

                            Toast.makeText(RegisterActivity.this, "Registration Failed. "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}