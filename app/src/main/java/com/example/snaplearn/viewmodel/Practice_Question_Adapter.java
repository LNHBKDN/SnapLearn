package com.example.snaplearn.viewmodel;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;

import java.util.ArrayList;
import java.util.List;

public class Practice_Question_Adapter extends RecyclerView.Adapter<Practice_Question_Adapter.CardViewHolder> {
    private List<FlashCard> cardList;
    private ArrayList<FlashCard> correctAnswers = new ArrayList<>();
    private Context context;

//    private ResultCallback resultCallback;
//    public Practice_Question_Adapter(ResultCallback callback) {
//        this.resultCallback = callback;
//    }
//    public void checkResults() {
//        int numberOfCorrectAnswers = correctAnswers.size();
//                resultCallback.onResultChecked(numberOfCorrectAnswers);
//    }
    public Practice_Question_Adapter(List<FlashCard> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }
    public interface ResultCallback {
        void onResultChecked(int numberOfCorrectAnswers);
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private EditText edtTerm;
        private EditText edtDefinition;
        protected LinearLayout foreground;

        public CardViewHolder(View itemView) {
            super(itemView);
            edtTerm = itemView.findViewById(R.id.edt_term);
            edtDefinition = itemView.findViewById(R.id.edt_definition);
            foreground = itemView.findViewById(R.id.layout_foreground);
        }
    }
    @NonNull
    @Override
    public Practice_Question_Adapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_pratice_relativelayout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Practice_Question_Adapter.CardViewHolder holder, int position) {
        FlashCard card = cardList.get(position);
//        holder.edtTerm.setText(card.getTerm());
        holder.edtDefinition.setText(card.getDefinition());
        holder.edtTerm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    checkAnswer(holder, card);
                    return true;
                }
                return false;
            }
        });

        holder.edtTerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkAnswer(holder, card);
                }
            }
        });
//        holder.edtTerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//                    String enteredTerm = holder.edtTerm.getText().toString();
//                    String cardTerm = card.getTerm();
//                    if (enteredTerm.equals(cardTerm)) {
//                        correctAnswers.add(card);
//                        holder.foreground.setBackgroundResource(R.drawable.flash_card_right);
//                    }else{
//                        correctAnswers.remove(card);
//                        holder.foreground.setBackgroundResource(R.drawable.flash_card_wrong);
//                    }
//                }
//            }
//        });
    }
    private void checkAnswer(Practice_Question_Adapter.CardViewHolder holder, FlashCard card) {
        String enteredTerm = holder.edtTerm.getText().toString();
        String cardTerm = card.getTerm();
        if (enteredTerm.equals(cardTerm)) {
            correctAnswers.add(card);
            holder.foreground.setBackgroundResource(R.drawable.flash_card_right);
        } else {
            correctAnswers.remove(card);
            holder.foreground.setBackgroundResource(R.drawable.flash_card_wrong);
        }
        // Ẩn bàn phím sau khi kiểm tra
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(holder.edtTerm.getWindowToken(), 0);
    }
    @Override
    public int getItemCount() {
        return cardList.size();
    }
    public void removeItem(int index){
        cardList.remove(index);
        notifyItemRemoved(index);
    }
    public void undoItem(FlashCard card, int index){
        cardList.add(index,card);
        notifyItemInserted(index);
    }
    public FlashCard getItem(int position) {
        if (cardList != null && position >= 0 && position < cardList.size()) {
            return cardList.get(position);
        }
        return null;
    }
}
