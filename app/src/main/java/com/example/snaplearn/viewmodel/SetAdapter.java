package com.example.snaplearn.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.Set;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class SetAdapter extends FirebaseRecyclerAdapter<Set, SetAdapter.SetHolder> {

    private SetClickListener setClickListener;
    private List<Set> deletedSets; // Store deleted sets for undo

    public SetAdapter(@NonNull FirebaseRecyclerOptions<Set> options, SetClickListener setClickListener) {
        super(options);
        this.setClickListener = setClickListener;
        this.deletedSets = new ArrayList<>();
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
        Set deletedSet = getItem(position);
        deletedSets.add(deletedSet);
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    public void undoDeleteItem(int position, Set deletedSet) {
        getSnapshots().getSnapshot(position).getRef().setValue(deletedSet);
        deletedSets.remove(deletedSet);
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
