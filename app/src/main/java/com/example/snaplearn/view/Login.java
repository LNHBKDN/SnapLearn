package com.example.snaplearn.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snaplearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textView;
    FirebaseAuth mAuth;

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        textView = findViewById(R.id.tv_reg);
        buttonLogin = findViewById(R.id.btn_login);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//<<<<<<< Quan
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(Login.this, "Plz enter Email", Toast.LENGTH_SHORT).show();
                editTextEmail.setError("You must fill the email field or imma kick ur ass");
                return;
            }
            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(Login.this, "Plz enter Password", Toast.LENGTH_SHORT).show();
                editTextPassword.setError("You must fill the password field or imma kick ur ass");
                return;
            }
                mAuth.signInWithEmailAndPassword(email, password)
=======
                String username = binding.edtUsername.getText().toString();
                String password = binding.edtPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(username, password)
//>>>>>>> master
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//<<<<<<< Quan
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    intent.putExtra("UID",uid);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
//=======
                                    // Move the UID retrieval here after successful login
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        String uid = currentUser.getUid();
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.putExtra("UID", uid);
                                        startActivity(intent);
                                    } else {
                                        // Handle the case where the user is unexpectedly null
                                        Toast.makeText(Login.this, "User is null", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
////        String uid = currentUser.getUid();
//
//        String uid = "9MkPhphsNDS9m7tawSRfk78qNq82";
//        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = binding.edtUsername.getText().toString();
//                String password = binding.edtPassword.getText().toString();
//                mAuth.signInWithEmailAndPassword(username,password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    Intent intent = new Intent(Login.this, MainActivity.class);
//                                    intent.putExtra("UID",uid);
//                                    startActivity(intent);
//                                }else{
//                                    Toast.makeText(Login.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//    }

//>//>>>>>> master

    }
}