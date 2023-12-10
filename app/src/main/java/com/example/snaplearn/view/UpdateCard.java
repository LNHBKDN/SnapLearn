package com.example.snaplearn.view;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityLoginBinding;
import com.example.snaplearn.databinding.ActivityUpdateCardBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.model.Set;
import com.example.snaplearn.viewmodel.CardAdapter;
import com.example.snaplearn.viewmodel.ItemTouchHelperListener;
import com.example.snaplearn.viewmodel.RVItemHelperListenerCard;
import com.example.snaplearn.viewmodel.RecylerViewItemTouchHelper;
import com.example.snaplearn.viewmodel.SetAdapter;
import com.example.snaplearn.viewmodel.UpdateCardDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCard extends AppCompatActivity implements ItemTouchHelperListener {

    private ActivityUpdateCardBinding binding;
    private String uid;
    private String setID;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private DatabaseReference setsReference2;
    private Window window;

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
        binding.rvCards.setLayoutManager(new LinearLayoutManager(UpdateCard.this));
        window = getWindow();
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
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RVItemHelperListenerCard(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvCards);

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
                CardAdapter adapter = new CardAdapter(cardList, uid, setID);
                binding.rvCards.setAdapter(adapter);
//                adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(FlashCard flashCard) {
//                        // Handle item click here
//                        // Example: Show a Toast with the term of the clicked card
////                        Toast.makeText(UpdateCard.this, "Clicked: " + flashCard.getTerm(), Toast.LENGTH_SHORT).show();
//                        UpdateCardDialogFragment dialogFragment = UpdateCardDialogFragment.newInstance(flashCard.getID(), flashCard.getIDSet()
//                                                                    ,flashCard.getTerm(), flashCard.getDefinition());
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof CardAdapter.CardViewHolder) {
            int swipedPosition = viewHolder.getAdapterPosition();
            CardAdapter adapter = (CardAdapter) binding.rvCards.getAdapter();

            if (adapter != null) {
                FlashCard swipedCard = adapter.getItem(swipedPosition);
                adapter.deleteItem(swipedPosition, uid, setID);
//                Snackbar.make(binding.rvCards, "Card deleted", Snackbar.LENGTH_LONG)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // If the user clicks Undo, add the card back to the adapter
//                                adapter.undoItem(swipedCard, swipedPosition);
//                            }
//                        })
//                        .show();
            }
        }
    }
    public void updateCard(String cardId, String updatedTerm, String updatedDefinition) {
        DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("users")
                .child(uid).child("sets").child(setID).child("listCard").child(cardId);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("term", updatedTerm);
        updateData.put("definition", updatedDefinition);

        cardRef.updateChildren(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle the success
                        // For example, refresh the RecyclerView to reflect changes
                        loadRV();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure
                        Toast.makeText(UpdateCard.this, "Failed to update card", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}