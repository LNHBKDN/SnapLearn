package com.example.snaplearn.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivitySetBinding;

public class SetMethod extends AppCompatActivity {

    private String uid;
    private String setID;
    private ActivitySetBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Intent getIntent = getIntent();
//        uid = getIntent.getStringExtra("UID");
//        setID =getIntent.getStringExtra("idSet");
        uid = "4pPPfxZrs5YPIPF91wRi3EAC7t72";
        setID = "-NkQpHDCWrJEUtLwXO3G";
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetMethod.this, UpdateCard.class);
                intent.putExtra("UID",uid);
                intent.putExtra("setID",setID);
                startActivity(intent);
            }
        });
        binding.btnFlashCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetMethod.this, FlashCard.class);

                intent.putExtra("UID",uid);
                intent.putExtra("setID",setID);
                startActivity(intent);
            }
        });
        binding.btnPratice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetMethod.this, Practice.class);

                intent.putExtra("UID",uid);
                intent.putExtra("setID",setID);
                startActivity(intent);
            }
        });
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetMethod.this, Write.class);

                intent.putExtra("UID",uid);
                intent.putExtra("setID",setID);
                startActivity(intent);
            }
        });
    }
}