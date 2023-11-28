package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateSet extends AppCompatActivity {
    private ActivityCreateSetBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText editText_nameSet;
    private EditText editText_description;
    private Button btn_create_set;
    private String IdSet;
    private ArrayList<FlashCard> cardList;
//    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_set);
        binding = ActivityCreateSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvCards.setLayoutManager(new LinearLayoutManager(this));
        binding.btnCreateSet.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        IdSet = "";
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
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void addSet(String Name,String Description){
        String ID= myRef.push().getKey();
        ArrayList<FlashCard> listFlashCard = new ArrayList<FlashCard>();
        myRef.child(ID).setValue(new Set(ID, Name, Description, listFlashCard)).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()){
                    Toast.makeText(CreateSet.this,"Create set successfully",Toast.LENGTH_SHORT).show();
                    binding.btnCreateSet.setVisibility(View.GONE);
                    binding.editTextNameSet.setEnabled(false);
                    binding.editTextDescription.setEnabled(false);
                    IdSet = ID;

                }else{
                    Log.d("DEBUG","Create set failed");
                }
            }
        });
    }
    public void addCard(String term, String definition){
        String ID= myRef.push().getKey();
        myRef.child(ID).setValue(new FlashCard(ID, IdSet, term, definition)).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()){
                    Toast.makeText(CreateSet.this,"Add card successfully",Toast.LENGTH_SHORT).show();
                    binding.editTextTerm.setText("");
                    binding.editTextDefinition.setText("");

                }else{
                    Log.d("DEBUG","Add card failed");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = myRef.orderByChild("IDSet").equalTo(IdSet);
        FirebaseRecyclerOptions<FlashCard> options =
                new FirebaseRecyclerOptions.Builder<FlashCard>()
                        .setQuery(myRef, FlashCard.class)
                        .build();
        FirebaseRecyclerAdapter<FlashCard, CreateSet.FlashCardHolder> adapter = new FirebaseRecyclerAdapter<FlashCard, CreateSet.FlashCardHolder>(options) {

            @Override
            public CreateSet.FlashCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.term_definition_relativelayout, parent, false);

                return new CreateSet.FlashCardHolder(view);
            }

            @Override
            protected void onBindViewHolder(CreateSet.FlashCardHolder holder, int position, FlashCard model) {
                holder.edtTerm.setText(model.getTerm());
                holder.edtDefinition.setText(model.getDefinition());
            }
        };
        binding.rvCards.setAdapter(adapter);
        adapter.startListening();
        // Listener for changes in the dataset for the specific ID
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the adapter's current data
                adapter.notifyDataSetChanged();
                // Update adapter with the new data
                adapter.startListening();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
            }
        };

        // Add the listener to the reference
        myRef.addValueEventListener(valueEventListener);
    }
    public static class FlashCardHolder extends RecyclerView.ViewHolder {
        private EditText edtTerm;
        private EditText edtDefinition;
        public FlashCardHolder(View view) {
            super(view);
            edtTerm = view.findViewById(R.id.editText_card_term);
            edtDefinition = view.findViewById(R.id.editText_card_description);
        }
    }
}