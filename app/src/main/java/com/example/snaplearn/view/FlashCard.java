package com.example.snaplearn.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.snaplearn.R;
import com.example.snaplearn.databinding.ActivityFlashCardBinding;
import com.example.snaplearn.model.Set;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FlashCard extends AppCompatActivity {
    private String uid;
    private String setID;
    private ActivityFlashCardBinding binding;
    private AnimatorSet frontAnimation;
    private AnimatorSet backAnimation;
    private boolean isFront = true;
    private DatabaseReference setsReference;
    private FirebaseDatabase database;
    private DatabaseReference listCardRef;
    private DatabaseReference setsReference2;
    private List<com.example.snaplearn.model.FlashCard> cardList;
    private FloatingActionButton btnLeft,btnRight;
    private TextView txtNum,txtSum,txtNameSet;
    TextView front,back;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFlashCardBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_flash_card);

        cardList = new ArrayList<>();

        Intent intent = getIntent();
        if(intent != null) {
            uid = intent.getStringExtra("UID");
            setID = intent.getStringExtra("setID");
        }

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        btnBack=findViewById(R.id.btn_back);
        txtNum=findViewById(R.id.txtNum);
        txtSum=findViewById(R.id.txtSum);
        txtNameSet=findViewById(R.id.txtNameSet);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        front = (TextView) findViewById(R.id.card_front);
        back = (TextView) findViewById(R.id.card_back);

        front.setCameraDistance(8000 * scale);
        back.setCameraDistance(8000 * scale);

        frontAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_animator);
        backAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_animator);
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFront) {
                    frontAnimation.setTarget(front);
                    backAnimation.setTarget(back);
                    frontAnimation.start();
                    backAnimation.start();
                    isFront = false;
                } else {
                    frontAnimation.setTarget(back);
                    backAnimation.setTarget(front);
                    backAnimation.start();
                    frontAnimation.start();
                    isFront = true;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFront) {
                    frontAnimation.setTarget(front);
                    backAnimation.setTarget(back);
                    frontAnimation.start();
                    backAnimation.start();
                    isFront = false;
                } else {
                    frontAnimation.setTarget(back);
                    backAnimation.setTarget(front);
                    backAnimation.start();
                    frontAnimation.start();
                    isFront = true;
                }
            }
        });
        btnLeft=findViewById(R.id.btn_left);
        btnRight=findViewById(R.id.btn_right);




        database = FirebaseDatabase.getInstance();
        setName();
        loadRV();
        getSum();
        txtNum.setText(1+"");

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num=Integer.parseInt(txtNum.getText().toString());
                num++;
                int sum=Integer.parseInt(txtSum.getText().toString());
                if(num<=sum)
                {
                    if(!isFront)
                    {
                        int finalNum = num;
                        back.setText("");
                        frontAnimation.setTarget(back);
                        backAnimation.setTarget(front);
                        int finalNum1 = num;
                        backAnimation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                displayFlashCard(cardList.get(finalNum1 -1));
                                txtNum.setText(finalNum1+"");
                            }
                        });
                        backAnimation.start();
                        frontAnimation.start();
                        isFront = true;
                    }
                    else {
                        displayFlashCard(cardList.get(num -1));
                        txtNum.setText(num+"");
                    }


                }
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num=Integer.parseInt(txtNum.getText().toString());
                num--;
                if(num>0)
                {
                    if(!isFront)
                    {
                        int finalNum = num;
                        back.setText("");
                        frontAnimation.setTarget(back);
                        backAnimation.setTarget(front);
                        int finalNum1 = num;
                        backAnimation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                displayFlashCard(cardList.get(finalNum1 -1));
                                txtNum.setText(finalNum1+"");
                            }
                        });
                        backAnimation.start();
                        frontAnimation.start();
                        isFront = true;
                    }
                    else {
                        displayFlashCard(cardList.get(num -1));
                        txtNum.setText(num+"");
                    }
                }
            }
        });

    }
    public void loadRV(){
        listCardRef = database.getReference("users").child(uid).child("sets").child(setID).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {

                    com.example.snaplearn.model.FlashCard card = cardSnapshot.getValue(com.example.snaplearn.model.FlashCard.class);
                    cardList.add(card);
                }

                displayFlashCard(cardList.get(0));

            }
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    public void setName()
    {
        setsReference = database.getReference("users").child(uid).child("sets").child(setID);

        setsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve data for the specific setID
                    Set setModel = dataSnapshot.getValue(Set.class);
                    txtNameSet.setText(setModel.getName());
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
    }
    public void getSum()
    {
        listCardRef = database.getReference("users").child(uid).child("sets").child(setID).child("listCard");
        listCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int num=0;
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    num++;
                }
                txtSum.setText(num+"");

            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void displayFlashCard(com.example.snaplearn.model.FlashCard flashCard) {
        if(flashCard!=null)
        {

            front.setText(flashCard.getTerm());
            back.setText(flashCard.getDefinition());
        }
        else {

            front.setText("");
            back.setText("");
        }
        // Hiển thị nội dung thẻ trên front và back của TextView
    }
}