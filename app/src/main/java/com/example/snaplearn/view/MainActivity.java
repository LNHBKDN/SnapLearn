package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityMainBinding;
import com.example.snaplearn.databinding.SetItemBinding;
import com.example.snaplearn.model.Set;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private FirebaseFirestore firestore;
    private ActivityMainBinding binding;
    private SetItemBinding setItemBinding;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent getIntent = getIntent();
        uid = getIntent.getStringExtra("UID");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users").child(uid).child("sets");

        firestore = FirebaseFirestore.getInstance();
        binding.rvSet.setLayoutManager(new LinearLayoutManager(this));
        binding.btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateSet.class);
                intent.putExtra("UID",uid);
                startActivity(intent);
            }
        });
        binding.mainSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSets(newText);
                return false;
            }
        });
    }
    private void setupRV(FirebaseRecyclerOptions<Set> options){
        FirebaseRecyclerAdapter<Set, SetHolder> adapter = new FirebaseRecyclerAdapter<Set, SetHolder>(options) {
            @Override
            public SetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.set_item, parent, false);
                return new SetHolder(view);
            }

            @Override
            protected void onBindViewHolder(SetHolder holder, int position, Set model) {
                holder.edtName.setText(model.getName());
                holder.edtDec.setText(model.getDescription());
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v){
                        handleLongClick(model.getID());
                        return true;
                    }
                });
            }
        };

        binding.rvSet.setAdapter(adapter);
        adapter.startListening();
    }
    private void handleLongClick(String idSet){
        Intent intent = new Intent(MainActivity.this, SetMethod.class);
        intent.putExtra("UID",uid);
        intent.putExtra("idSet",idSet);
        startActivity(intent);

    }
    private void searchSets(String query) {

        FirebaseRecyclerOptions<Set> options;
        if(query.isEmpty()){
            options = new FirebaseRecyclerOptions.Builder<Set>()
                    .setQuery(myRef,Set.class)
                    .build();
        }
        else {
            options =new FirebaseRecyclerOptions.Builder<Set>()
                    .setQuery(myRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff"), Set.class)
                    .build();
        }
        setupRV(options);


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Set> options =
                new FirebaseRecyclerOptions.Builder<Set>()
                        .setQuery(myRef, Set.class)
                        .build();
        setupRV(options);
    }
    public static class SetHolder extends RecyclerView.ViewHolder {
        private EditText edtName;
        private EditText edtDec;
        public SetHolder(View view) {
            super(view);
            edtName = view.findViewById(R.id.edt_name);
            edtDec = view.findViewById(R.id.edt_descrip);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}