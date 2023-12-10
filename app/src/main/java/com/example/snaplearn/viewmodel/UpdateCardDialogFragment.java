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

    public static UpdateCardDialogFragment newInstance(String so_cau_dung) {
        UpdateCardDialogFragment fragment = new UpdateCardDialogFragment();
        Bundle args = new Bundle();
        args.putString("so_cau_dung", so_cau_dung);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            term = getArguments().getString("so_cau_dung");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_update_card, null);
        editTextTerm = view.findViewById(R.id.et_term);

        editTextTerm.setText(term);

        builder.setView(view)
                .setPositiveButton("Reset exam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        String updatedTerm = editTextTerm.getText().toString();
//                        String updatedDefinition = editTextDefinition.getText().toString();
//                        ((UpdateCard) requireActivity()).updateCard(cardId, updatedTerm, updatedDefinition);
                    }
                })
                .setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
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

