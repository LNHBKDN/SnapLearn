package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityPracticeBinding;
import com.example.snaplearn.databinding.ActivityWriteBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.viewmodel.CardAdapter;
import com.example.snaplearn.viewmodel.ItemExamAdapter;
import com.example.snaplearn.viewmodel.Practice_Question_Adapter;
import com.example.snaplearn.viewmodel.UpdateCardDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Write extends AppCompatActivity {
    private ActivityWriteBinding binding;
    private String IdSet;
    private String uid;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private  ItemExamAdapter adapter;
    private List<FlashCard> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        IdSet = getIntent.getStringExtra("setID");
        database = FirebaseDatabase.getInstance();
        setsReference = database.getReference("users").child(uid).child("sets").child(IdSet);
        binding.rvExam.setLayoutManager(new LinearLayoutManager(Write.this,LinearLayoutManager.HORIZONTAL, false));
        fetchFlashCardsFromFirebase();
        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String so_cau_dung = String.valueOf(adapter.getSo_cau_dung());
                Log.d("Check",so_cau_dung);
            }
        });

    }
    private void fetchFlashCardsFromFirebase() {
        listCardRef = database.getReference("users").child(uid).child("sets").child(IdSet).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<com.example.snaplearn.model.FlashCard> cardList = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    com.example.snaplearn.model.FlashCard card = cardSnapshot.getValue(FlashCard.class);
                    cardList.add(card);
                    Log.d("LoadData", card.getTerm());
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(Write.this);
                binding.rvExam.setLayoutManager(layoutManager);
                adapter = new ItemExamAdapter( cardList);
                binding.rvExam.setAdapter(adapter);

            }
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}