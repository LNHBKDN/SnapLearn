package com.example.snaplearn.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityCreateSetBinding;
import com.example.snaplearn.databinding.ActivityPracticeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Practice extends AppCompatActivity {
    private ActivityPracticeBinding binding;
    private String IdSet;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        IdSet = getIntent.getStringExtra("setID");
    }
}