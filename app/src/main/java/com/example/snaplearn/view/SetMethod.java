package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivitySetBinding;
import com.example.snaplearn.viewmodel.Practice_Question_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetMethod extends AppCompatActivity {

    private String uid;
    private String setID;
    private ActivitySetBinding binding;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private DatabaseReference setsReference2;
    List<com.example.snaplearn.model.FlashCard> cardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        setID =getIntent.getStringExtra("idSet");
        database = FirebaseDatabase.getInstance();
        setsReference = database.getReference("users").child(uid).child("sets").child(setID);
//        uid = "4pPPfxZrs5YPIPF91wRi3EAC7t72";
//        setID = "-NkQpHDCWrJEUtLwXO3G";
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetMethod.this, UpdateCard.class);
                intent.putExtra("UID",uid);
                intent.putExtra("setID",setID);
                startActivity(intent);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                listCardRef = database.getReference("users").child(uid).child("sets").child(setID).child("listCard");
                listCardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cardSize = (int) dataSnapshot.getChildrenCount();

                        Log.d("card_size", String.valueOf(cardSize));

                        if (cardSize < 4) {
                            showToast("You need at least 4 flashcards to proceed.");
                        } else {
                            Intent intent = new Intent(SetMethod.this, Write.class);
                            intent.putExtra("UID", uid);
                            intent.putExtra("setID", setID);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu cần
                    }
                });
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(SetMethod.this, message, Toast.LENGTH_SHORT).show();
    }
}