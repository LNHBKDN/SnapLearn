package com.example.snaplearn.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.snaplearn.R;

public class SetMethod extends AppCompatActivity {

    private String uid;
    private String setID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        setID =getIntent.getStringExtra("idSet");
    }
}