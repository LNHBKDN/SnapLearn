package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityLoginBinding;
import com.example.snaplearn.databinding.ActivityUpdateCardBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.model.Set;
import com.example.snaplearn.viewmodel.CardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCard extends AppCompatActivity {

    private ActivityUpdateCardBinding binding;
    private String uid;
    private String setID;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private DatabaseReference setsReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent != null) {
            uid = intent.getStringExtra("UID");
            setID = intent.getStringExtra("setID");

        }
        database = FirebaseDatabase.getInstance();
        setsReference = database.getReference("users").child(uid).child("sets").child(setID);

        setsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data for the specific setID
                    Set setModel = dataSnapshot.getValue(Set.class);
                    binding.editTextNameSet.setText(setModel.getName());
                    binding.editTextDescription.setText(setModel.getDescription());
                    // Now you can use setModel to access the data
                } else {
                    // Handle if the setID doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
        loadRV();
        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = binding.editTextTerm.getText().toString();
                String definition = binding.editTextDefinition.getText().toString();
                addCard(term, definition);
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameSet = binding.editTextNameSet.getText().toString();
                String description = binding.editTextDescription.getText().toString();
                updateSet(nameSet, description);
                onBackPressed();
            }
        });
    }

    public void loadRV(){
        listCardRef = database.getReference("users").child(uid).child("sets").child(setID).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<com.example.snaplearn.model.FlashCard> cardList = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    com.example.snaplearn.model.FlashCard card = cardSnapshot.getValue(FlashCard.class);
                    cardList.add(card);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(UpdateCard.this);
                binding.rvCards.setLayoutManager(layoutManager);
                // Hiển thị dữ liệu trong RecyclerView bằng cách sử dụng Adapter
                CardAdapter adapter = new CardAdapter(cardList);
                binding.rvCards.setAdapter(adapter);
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
    }
    public void addCard(String term, String definition) {
        String cardId = database.getReference("users").child(uid).child("sets").child(setID).child("listCard").push().getKey(); // Tạo cardID mới
        FlashCard flashCard = new FlashCard(cardId, setID, term, definition);

        // Lấy tham chiếu đến danh sách thẻ
        DatabaseReference listCardRef = database.getReference("users").child(uid).child("sets").child(setID).child("listCard");

        // Thêm thẻ mới vào danh sách
        listCardRef.child(cardId).setValue(flashCard).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateCard.this, "Add card successfully", Toast.LENGTH_SHORT).show();
                    binding.editTextTerm.setText("");
                    binding.editTextDefinition.setText("");

                } else {
                    Log.d("DEBUG", "Add card failed");
                }
            }
        });

    }
    private void updateSet(String nameSet, String description) {
        // Tạo một HashMap chứa các giá trị muốn cập nhật
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("name", nameSet); // Giá trị mới cho trường "name"
        updateData.put("description", description); // Giá trị mới cho trường "description"
        // Thực hiện cập nhật bằng phương thức updateChildren()
        setsReference.updateChildren(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thành công
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi cập nhật thất bại
                    }
                });

    }

}