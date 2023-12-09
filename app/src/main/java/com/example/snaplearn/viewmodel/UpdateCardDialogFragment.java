package com.example.snaplearn.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;
import com.example.snaplearn.view.UpdateCard;

public class UpdateCardDialogFragment extends DialogFragment {
    private EditText editTextTerm;
    private EditText editTextDefinition;

    private String cardId;
    private String setID;
    private String term;
    private String definition;

    public UpdateCardDialogFragment() {
        // Required empty public constructor
    }

    public static UpdateCardDialogFragment newInstance(String cardId, String setID, String term, String definition) {
        UpdateCardDialogFragment fragment = new UpdateCardDialogFragment();
        Bundle args = new Bundle();
        args.putString("cardId", cardId);
        args.putString("setID", setID);
        args.putString("term", term);
        args.putString("definition", definition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardId = getArguments().getString("cardId");
            setID = getArguments().getString("setID");
            term = getArguments().getString("term");
            definition = getArguments().getString("definition");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_update_card, null);
        editTextTerm = view.findViewById(R.id.et_term);
        editTextDefinition = view.findViewById(R.id.et_definition);

        editTextTerm.setText(term);
        editTextDefinition.setText(definition);

        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String updatedTerm = editTextTerm.getText().toString();
                        String updatedDefinition = editTextDefinition.getText().toString();
                        ((UpdateCard) requireActivity()).updateCard(cardId, updatedTerm, updatedDefinition);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Window window = ((Activity) requireContext()).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 1.0f; // Trở lại độ sáng ban đầu
        window.setAttributes(params);
    }
}

