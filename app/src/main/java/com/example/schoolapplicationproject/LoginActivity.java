package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.schoolapplicationproject.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.tfEmail.getText().toString();
                String password = binding.tfPassword.getText().toString();
                int radioButton = binding.radioGroupType.getCheckedRadioButtonId();
                RadioButton radioBtn = findViewById(radioButton);
                final String typeRB = radioBtn.getText().toString();

                if (email.isEmpty()) {
                    binding.tfEmail.setError("Email is required.");
                    return;
                }
                if (password.isEmpty()) {
                    binding.tfPassword.setError("Password is required.");
                    return;
                }
                if (password.length() < 6) {
                    binding.tfPassword.setError("Password must have minimum 6 characters.");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        String type = document.getString("type");
                                        if (typeRB.equals(type) && type.equals("Teacher")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (typeRB.equals(type) && type.equals("Parent")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), ParentMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Registration Failed. "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}