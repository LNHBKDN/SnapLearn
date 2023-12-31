package com.example.snaplearn.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snaplearn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    TextInputEditText reEditTextPassword;
    Button buttonReg;
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
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        reEditTextPassword = findViewById(R.id.repassword);
        buttonReg = findViewById(R.id.btn_register);

        TextView textView;
        textView = findViewById(R.id.tv_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(v -> {
            String email, password, rePassword;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            rePassword = String.valueOf(reEditTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(Register.this, "Plz Enter Email?", Toast.LENGTH_SHORT).show();
                editTextEmail.setError("You must fill the email field");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                //Toast.makeText(Register.this, "Plz Enter Password?", Toast.LENGTH_SHORT).show();
                editTextPassword.setError("You must fill the email field");
                return;
            }
            if (TextUtils.isEmpty(rePassword) || !rePassword.equals(password)) {
                //Toast.makeText(Register.this, "Plz Enter Password?", Toast.LENGTH_SHORT).show();
                reEditTextPassword.setError("Re-confirm the password");
                return;
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "You have registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        });
    }
}
