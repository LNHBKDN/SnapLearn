package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.model.Set;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateSet extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText editText_nameSet;
    private EditText editText_description;
    private Button btn_create_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_set);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("posts");
        editText_nameSet.findViewById(R.id.editText_nameSet);
        editText_description.findViewById(R.id.editText_description);
        btn_create_set.findViewById(R.id.btn_create_set);

        btn_create_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editText_nameSet.getText().toString();
                String Description = editText_description.getText().toString();
                addSet(Name, Description);
            }
        });
    }
    public void addSet(String Name,String Description){
        String ID= myRef.push().getKey();
        ArrayList<FlashCard> listFlashCard = new ArrayList<FlashCard>();
        myRef.child(ID).setValue(new Set(ID, Name, Description, listFlashCard)).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()){
                    Log.d("DEBUG","Create set sucessfully");
                }else{
                    Log.d("DEBUG","Create set failed");
                }
            }
        });
    }
}