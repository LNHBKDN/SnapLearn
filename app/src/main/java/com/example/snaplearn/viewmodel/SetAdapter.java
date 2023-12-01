package com.example.snaplearn.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.Set;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends FirebaseRecyclerAdapter<Set, SetAdapter.SetHolder> {

    private SetClickListener setClickListener;


    public SetAdapter(@NonNull FirebaseRecyclerOptions<Set> options, SetClickListener setClickListener) {
        super(options);
        this.setClickListener = setClickListener;

    }

    @Override
    protected void onBindViewHolder(@NonNull SetHolder holder, int position, @NonNull Set model) {
        holder.edtName.setText(model.getName());
        holder.edtDec.setText(model.getDescription());
        holder.layoutForeGround.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(model.getID());
                return true;
            }
        });
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_item, parent, false);
        return new SetHolder(view);
    }

    private void handleLongClick(String idSet) {
        setClickListener.onSetLongClick(idSet);
    }

    public void removeItem(int position) {
        DatabaseReference setRef = getRef(position);

        // Remove the set from the database
        setRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Item removed successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure to remove the item
                    }
                });
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public void undoDeleteItem(int position, Set deletedSet) {
        // Get the reference to the location where the item was deleted from
        DatabaseReference setRef = getRef(position);

        // Add the deleted set back to the same location
        setRef.setValue(deletedSet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Undo successful
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure to undo the deletion
                    }
                });
    }


    public interface SetClickListener {
        void onSetLongClick(String idSet);
    }

    public static class SetHolder extends RecyclerView.ViewHolder {
        private EditText edtName;
        private EditText edtDec;
        protected LinearLayout layoutForeGround;

        public SetHolder(View view) {
            super(view);
            edtName = view.findViewById(R.id.edt_name);
            edtDec = view.findViewById(R.id.edt_descrip);
            layoutForeGround = view.findViewById(R.id.layout_foreground);
        }
    }
}
