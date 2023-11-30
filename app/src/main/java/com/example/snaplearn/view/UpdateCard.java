package com.example.snaplearn.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityLoginBinding;
import com.example.snaplearn.databinding.ActivityUpdateCardBinding;

public class UpdateCard extends AppCompatActivity {

    private ActivityUpdateCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}