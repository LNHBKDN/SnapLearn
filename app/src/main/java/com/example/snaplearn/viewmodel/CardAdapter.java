package com.example.snaplearn.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            foreground = itemView.findViewById(R.id.foreground);
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
    public void deleteItem(int position,String uid, String setID) {
        FlashCard deletedCard = cardList.get(position);
        cardList.remove(position);
        notifyItemRemoved(position);
        deleteCardFromDatabase(deletedCard,uid,setID);
    }

    private void deleteCardFromDatabase(FlashCard card,String uid, String setID) {
        // Assuming you have a unique card ID, use it to delete the card from the database
        String cardId = card.getID();
        DatabaseReference cardRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("sets")
                .child(setID)
                .child("listCard")
                .child(cardId);

        // Remove the card from the database
        cardRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DEBUG", "Delete card failed");
                    }
                });
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
