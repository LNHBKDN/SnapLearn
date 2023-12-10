package com.example.snaplearn.viewmodel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ItemExamAdapter extends RecyclerView.Adapter<ItemExamAdapter.IEHolder> {
    private List<FlashCard> cardList;
    private Random random = new Random();
    private int So_cau_dung = 0;


    private boolean restart = false;
    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public int getSo_cau_dung() {
        return So_cau_dung;
    }

    private boolean isCorrectAnswerSelected = false;

    public ItemExamAdapter(List<FlashCard> cardList) {
        this.cardList = cardList;
    }
    @NonNull
    @Override
    public ItemExamAdapter.IEHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
        return new ItemExamAdapter.IEHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IEHolder holder, int position) {
        FlashCard card = cardList.get(position);
        holder.tv_ask.setText(card.getDefinition());
        if(restart == true){
            So_cau_dung = 0;
            holder.rd_group.clearCheck();
            holder.rdbtn_A.setChecked(false);
            holder.rdbtn_B.setChecked(false);
            holder.rdbtn_C.setChecked(false);
            holder.rdbtn_D.setChecked(false);
            restart = false;
        }

        List<String> allTermsExceptCorrect = new ArrayList<>();
        for (FlashCard flashCard : cardList) {
            if (!flashCard.getTerm().equals(card.getTerm())) {
                allTermsExceptCorrect.add(flashCard.getTerm());
            }
        }

        allTermsExceptCorrect.remove(card.getTerm());

        Collections.shuffle(allTermsExceptCorrect);

        Collections.shuffle(allTermsExceptCorrect);

        List<String> answerChoices = new ArrayList<>();
        answerChoices.add(allTermsExceptCorrect.get(0));
        answerChoices.add(allTermsExceptCorrect.get(1));
        answerChoices.add(allTermsExceptCorrect.get(2));
        answerChoices.add(allTermsExceptCorrect.get(3));
        Collections.shuffle(answerChoices);


        setAnswerToButton(holder.rdbtn_A, answerChoices.get(0));
        setAnswerToButton(holder.rdbtn_B, answerChoices.get(1));
        setAnswerToButton(holder.rdbtn_C, answerChoices.get(2));
        setAnswerToButton(holder.rdbtn_D, answerChoices.get(3));


        List<RadioButton> radioButtons = new ArrayList<>();
        radioButtons.add(holder.rdbtn_A);
        radioButtons.add(holder.rdbtn_B);
        radioButtons.add(holder.rdbtn_C);
        radioButtons.add(holder.rdbtn_D);
        Collections.shuffle(radioButtons);

        setCorrectAnswer(radioButtons.get(random.nextInt(4)), card.getTerm());

        holder.rd_group.setOnCheckedChangeListener(null);

        holder.rd_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = group.findViewById(checkedId);
            if (selectedRadioButton != null && selectedRadioButton.isChecked()) {
                String selectedAnswer = selectedRadioButton.getText().toString();

                if (card.getTerm().equals(selectedAnswer)) {
                    So_cau_dung++;
                }
            }
        });
    }

    private void setCorrectAnswer(RadioButton radioButton, String correctAnswer) {
        radioButton.setText(correctAnswer);
    }
    private void setAnswerToButton(RadioButton radioButton, String answer) {
        radioButton.setText(answer);
    }

    public int getItemCount() {
        return cardList.size();
    }
    public static class IEHolder extends RecyclerView.ViewHolder {
        private EditText tv_ask;
        private RadioGroup rd_group;
        private RadioButton rdbtn_A;
        private RadioButton rdbtn_B;
        private RadioButton rdbtn_C;
        private RadioButton rdbtn_D;
        protected LinearLayout layoutForeGround;

        public IEHolder(View view) {
            super(view);
            tv_ask = view.findViewById(R.id.tv_ask);
            rd_group = view.findViewById(R.id.rdio_group);
            rdbtn_A = view.findViewById(R.id.rdbtn_A);
            rdbtn_B = view.findViewById(R.id.rdbtn_B);
            rdbtn_C = view.findViewById(R.id.rdbtn_C);
            rdbtn_D = view.findViewById(R.id.rdbtn_D);
            layoutForeGround = view.findViewById(R.id.layout_foreground);
        }
    }
}
