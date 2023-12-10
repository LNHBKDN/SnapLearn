package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityCreateSetBinding;
import com.example.snaplearn.databinding.ActivityPracticeBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.viewmodel.CardAdapter;
import com.example.snaplearn.viewmodel.Practice_Question_Adapter;
import com.example.snaplearn.viewmodel.UpdateCardDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Practice extends AppCompatActivity implements Practice_Question_Adapter.ResultCallback {
    private ActivityPracticeBinding binding;
    private String IdSet;
    private String uid;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private DatabaseReference setsReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        IdSet = getIntent.getStringExtra("setID");
        database = FirebaseDatabase.getInstance();
        setsReference = database.getReference("users").child(uid).child("sets").child(IdSet);
        binding.rvQuestions.setLayoutManager(new LinearLayoutManager(Practice.this));
        loadRV();
        binding.btnCheckResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkResults();
            }
        });
    }
    public void loadRV(){
        listCardRef = database.getReference("users").child(uid).child("sets").child(IdSet).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<com.example.snaplearn.model.FlashCard> cardList = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    com.example.snaplearn.model.FlashCard card = cardSnapshot.getValue(com.example.snaplearn.model.FlashCard.class);
                    cardList.add(card);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(Practice.this);
                binding.rvQuestions.setLayoutManager(layoutManager);
                Practice_Question_Adapter adapter = new Practice_Question_Adapter( cardList,Practice.this);
                binding.rvQuestions.setAdapter(adapter);
//                adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(FlashCard flashCard) {
//                        // Handle item click here
//                        // Example: Show a Toast with the term of the clicked card
////                        Toast.makeText(UpdateCard.this, "Clicked: " + flashCard.getTerm(), Toast.LENGTH_SHORT).show();
//                        UpdateCardDialogFragment dialogFragment = UpdateCardDialogFragment.newInstance(flashCard.getID(), flashCard.getIDSet()
//                                ,flashCard.getTerm(), flashCard.getDefinition());
//                        WindowManager.LayoutParams params = window.getAttributes();
//                        params.alpha = 0.5f; // Giảm độ sáng của cửa sổ xuống (ví dụ 0.5 làm cho nó trở nên tối đi)
//                        window.setAttributes(params);
//                        dialogFragment.show(getSupportFragmentManager(), "UpdateCardDialogFragment");
//                    }
//                });

            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
    }
    private void checkResults() {
        Practice_Question_Adapter adapter = (Practice_Question_Adapter) binding.rvQuestions.getAdapter();
        if (adapter != null) {
//            adapter.checkResults();
        }
    }
    @Override
    public void onResultChecked(int numberOfCorrectAnswers) {
        showResultDialog(numberOfCorrectAnswers);
    }
    private void showResultDialog(int numberOfCorrectAnswers) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả");
        builder.setMessage("Số câu đúng: " + numberOfCorrectAnswers);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}