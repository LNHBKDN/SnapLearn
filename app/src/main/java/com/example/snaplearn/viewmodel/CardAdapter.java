package com.example.snaplearn.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

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
    // ViewHolder để giữ các thành phần giao diện của mỗi item
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private EditText edtTerm;
        private EditText edtDefinition;
        // Các thành phần giao diện khác của card

        public CardViewHolder(View itemView) {
            super(itemView);
            edtTerm = itemView.findViewById(R.id.editText_card_term);
            edtDefinition = itemView.findViewById(R.id.editText_card_description);
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
        // Gắn dữ liệu từ danh sách card vào ViewHolder
        FlashCard card = cardList.get(position);
        holder.edtTerm.setText(card.getTerm());
        holder.edtDefinition.setText(card.getDefinition());
        // Gắn các thông tin khác của card vào các thành phần giao diện tương ứng
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}