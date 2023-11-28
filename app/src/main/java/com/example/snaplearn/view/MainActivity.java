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
import android.widget.TextView;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityMainBinding;
import com.example.snaplearn.databinding.SetItemBinding;
import com.example.snaplearn.model.Set;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database ;
    private DatabaseReference myRef;
    private FirebaseFirestore firestore;
    private ActivityMainBinding binding;
    private SetItemBinding setItemBinding;
    private static int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("posts");

        firestore = FirebaseFirestore.getInstance();
        binding.rvSet.setLayoutManager(new LinearLayoutManager(this));
        binding.btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Set newData = new Set();
//                newData.setID("1");
//                newData.setName("Test1");
//                newData.setDescription("Just a test");
//
//                myRef.push().setValue(newData);
                Intent intent = new Intent(MainActivity.this,CreateSet.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Set> options =
                new FirebaseRecyclerOptions.Builder<Set>()
                        .setQuery(myRef, Set.class)
                        .build();
        FirebaseRecyclerAdapter<Set, PostHolder> adapter = new FirebaseRecyclerAdapter<Set, PostHolder>(options) {

            @Override
            public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.set_item, parent, false);

                return new PostHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostHolder holder, int position, Set model) {
                holder.edtSet.setText("set" + count);
                holder.edtName.setText(model.getName());
                holder.edtDec.setText(model.getDescription());
            }
        };
        binding.rvSet.setAdapter(adapter);
        adapter.startListening();
    }
    public static class PostHolder extends RecyclerView.ViewHolder {
        private EditText edtSet;
        private EditText edtName;
        private EditText edtDec;
        public PostHolder(View view) {
            super(view);
            edtSet = view.findViewById(R.id.edt_setname);
            edtName = view.findViewById(R.id.edt_name);
            edtDec = view.findViewById(R.id.edt_descrip);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public static int counting(){
        count++;
        return count;
    }
}