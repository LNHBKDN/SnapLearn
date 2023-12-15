package com.example.snaplearn.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    ActivityLoginBinding activityLoginBinding;
    private String uid;
    LoadingDialog loadingDialog = new LoadingDialog(Login.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        setContentView(view);
        FirebaseUser currentUser = mAuth.getCurrentUser();



        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activityLoginBinding.email.getText().toString();
                String password = activityLoginBinding.password.getText().toString();

                if (TextUtils.isEmpty(email)) {
//                Toast.makeText(Register.this, "Plz Enter Email?", Toast.LENGTH_SHORT).show();
                    activityLoginBinding.email.setError("You must fill the email field");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //Toast.makeText(Register.this, "Plz Enter Password?", Toast.LENGTH_SHORT).show();
                    activityLoginBinding.password.setError("You must fill the email field");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    loadingDialog.startLoadingDialog();
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            loadingDialog.closeLoadingDialog();
//                                        }
//                                    }, 2000);
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    uid = currentUser.getUid();
                                    intent.putExtra("UID",uid);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Login.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        TextView textView;
        textView = findViewById(R.id.tv_reg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
