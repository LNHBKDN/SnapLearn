package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityCreateSetBinding;
import com.example.snaplearn.databinding.ActivityLoginBinding;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.model.Set;
import com.example.snaplearn.viewmodel.CardAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class CreateSet extends AppCompatActivity {
    private ActivityCreateSetBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference setsReference;
    private EditText editText_nameSet;
    private EditText editText_description;
    private Button btn_create_set;
    private String IdSet;
    private String uid;
    private ArrayList<FlashCard> cardList;
    private DatabaseReference listCardRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        binding.rvCards.setLayoutManager(new LinearLayoutManager(this));
        binding.btnCreateSet.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setsReference = database.getReference("users").child(uid).child("sets");

        IdSet = "";
        binding.rvCards.setVisibility(View.GONE);
        binding.editTextTerm.setVisibility(View.GONE);
        binding.editTextDefinition.setVisibility(View.GONE);
        binding.btnAddCard.setVisibility(View.GONE);
        binding.btnCreateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = binding.editTextNameSet.getText().toString();
                String Description = binding.editTextDescription.getText().toString();
                addSet(Name, Description);

            }
        });
        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String term = binding.editTextTerm.getText().toString();
                String definition = binding.editTextDefinition.getText().toString();
                addCard(term, definition);
            }
        });
    }
    public void addSet(String Name,String Description){
        String setId = setsReference.push().getKey();
        IdSet = setId;
        ArrayList<FlashCard> listFlashCard = new ArrayList<FlashCard>();
        Set set = new Set(setId, Name, Description, listFlashCard);

        setsReference.child(setId).setValue(set).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()){
                    Toast.makeText(CreateSet.this,"Create set successfully",Toast.LENGTH_SHORT).show();
                    binding.btnCreateSet.setVisibility(View.GONE);
                    binding.editTextNameSet.setEnabled(false);
                    binding.editTextDescription.setEnabled(false);
                    binding.rvCards.setVisibility(View.VISIBLE);
                    binding.editTextTerm.setVisibility(View.VISIBLE);
                    binding.editTextDefinition.setVisibility(View.VISIBLE);
                    binding.btnAddCard.setVisibility(View.VISIBLE);

                }else{
                    Log.d("DEBUG","Create set failed");
                }
            }
        });
        listCardRef = setsReference.child(IdSet).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FlashCard> cardList = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    FlashCard card = cardSnapshot.getValue(FlashCard.class);
                    cardList.add(card);
                }

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
        String setID = IdSet;
        String cardId = setsReference.child(setID).child("listCard").push().getKey(); // Tạo cardID mới
        FlashCard flashCard = new FlashCard(cardId, setID, term, definition);

        // Lấy tham chiếu đến danh sách thẻ
        DatabaseReference listCardRef = setsReference.child(setID).child("listCard");

        // Thêm thẻ mới vào danh sách
        listCardRef.child(cardId).setValue(flashCard).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateSet.this, "Add card successfully", Toast.LENGTH_SHORT).show();
                    binding.editTextTerm.setText("");
                    binding.editTextDefinition.setText("");

                } else {
                    Log.d("DEBUG", "Add card failed");
                }
            }
        });

    }
    protected void  onStart(){
        super.onStart();
        //listCardRef = setsReference.child(IdSet).child("listCard");

    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//                FirebaseRecyclerOptions<FlashCard> options =
//                        new FirebaseRecyclerOptions.Builder<FlashCard>()
//                                .setQuery(setsReference.child(IdSet).child("listCard"), FlashCard.class)
//                                .build();
//
//                FirebaseRecyclerAdapter<FlashCard, FlashCardHolder> adapter =
//                        new FirebaseRecyclerAdapter<FlashCard, FlashCardHolder>(options) {
//
//                            @Override
//                            public FlashCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                                View view = LayoutInflater.from(parent.getContext())
//                                        .inflate(R.layout.term_definition_relativelayout, parent, false);
//
//                                return new FlashCardHolder(view);
//                            }
//
//                            @Override
//                            protected void onBindViewHolder(FlashCardHolder holder, int position, FlashCard model) {
//                                holder.edtTerm.setText(model.getTerm());
//                                holder.edtDefinition.setText(model.getDefinition());
//                            }
//                        };
//
//                binding.rvCards.setAdapter(adapter);
//                adapter.startListening();
//
//    }
//
//    public static class FlashCardHolder extends RecyclerView.ViewHolder {
//        private EditText edtTerm;
//        private EditText edtDefinition;
//        public FlashCardHolder(View view) {
//            super(view);
//            edtTerm = view.findViewById(R.id.editText_card_term);
//            edtDefinition = view.findViewById(R.id.editText_card_description);
//        }
//    }
}