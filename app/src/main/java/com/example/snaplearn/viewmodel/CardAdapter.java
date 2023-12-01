package com.example.snaplearn.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<FlashCard> cardList;
    public CardAdapter(List<FlashCard> cardList) {
        this.cardList = cardList;
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private EditText edtTerm;
        private EditText edtDefinition;
        protected LinearLayout foreground;

        public CardViewHolder(View itemView) {
            super(itemView);
            edtTerm = itemView.findViewById(R.id.editText_card_term);
            edtDefinition = itemView.findViewById(R.id.editText_card_description);
            foreground = itemView.findViewById(R.id.layout_foreground);
        }
    }
    @NonNull
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_definition_relativelayout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder holder, int position) {
        FlashCard card = cardList.get(position);
        holder.edtTerm.setText(card.getTerm());
        holder.edtDefinition.setText(card.getDefinition());

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
