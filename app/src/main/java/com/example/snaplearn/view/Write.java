package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityPracticeBinding;
import com.example.snaplearn.databinding.ActivityWriteBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.viewmodel.ItemExamAdapter;
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
    private List<FlashCard> cardList;
    private ItemExamAdapter examAdapter;

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
        listCardRef = database.getReference("users").child(uid).child("sets").child(IdSet).child("listCard");
        binding.rvExam.setLayoutManager(new LinearLayoutManager(Write.this));
        examAdapter = new ItemExamAdapter(new ArrayList<>());
        binding.rvExam.setAdapter(examAdapter);
        cardList = new ArrayList<>();
        fetchFlashCardsFromFirebase();
//        PagerSnapHelper psh = new PagerSnapHelper();
//        psh.attachToRecyclerView(binding.rvExam);
    }
    private void fetchFlashCardsFromFirebase() {
        listCardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ dataSnapshot và đổ vào cardList
                    cardList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FlashCard flashCard = snapshot.getValue(FlashCard.class);
                        cardList.add(flashCard);
                        examAdapter.notifyDataSetChanged();
                    }

                    // Kiểm tra xem dữ liệu đã lấy thành công không
                    for (FlashCard card : cardList) {
                        Log.d("CardInfo", "Term: " + card.getTerm() + ", Definition: " + card.getDefinition());
                    }

                    // Cập nhật adapter sau khi có dữ liệu
                    examAdapter.notifyDataSetChanged();
                } else {
                    Log.d("FetchData", "Data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
                Log.e("FetchData", "Error fetching data: " + databaseError.getMessage());
            }
        });
    }
}