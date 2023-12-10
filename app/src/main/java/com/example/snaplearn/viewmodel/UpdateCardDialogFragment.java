package com.example.snaplearn.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
    private ItemExamAdapter adapter;
    private ResetExamListener resetExamListener;
    public void setResetExamListener(ResetExamListener listener) {
        this.resetExamListener = listener;
    }

    public UpdateCardDialogFragment() {
        // Required empty public constructor
    }
    public interface ResetExamListener {
        void onResetExam();
    }
    public static UpdateCardDialogFragment newInstance(String so_cau_dung,String so_cau) {
        UpdateCardDialogFragment fragment = new UpdateCardDialogFragment();
        Bundle args = new Bundle();
        args.putString("so_cau_dung", so_cau_dung);
        args.putString("so_cau", so_cau);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            term = "Số câu đúng" + getArguments().getString("so_cau_dung")+ "/"
                    +getArguments().getString("so_cau");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_update_card, null);
        editTextTerm = view.findViewById(R.id.et_term);
        editTextTerm.setText(term);
        Button btnResetExam = view.findViewById(R.id.btnResetExam);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        btnResetExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ResetExamDebug", "Reset exam button clicked");

                if (resetExamListener != null) {
                    Log.d("ResetExamDebug", "Calling onResetExam");
                    resetExamListener.onResetExam();
                } else {
                    Log.e("ResetExamDebug", "resetExamListener is null");
                }

                if (adapter != null) {
                    Log.d("ResetExamDebug", "Setting adapter restart to true");
                    adapter.setRestart(true);
                } else {
                    Log.e("ResetExamDebug", "Adapter is null");
                }

                // Dismiss the dialog if needed
                dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Confirm button click if needed
                dismiss();
            }
        });
//        builder.setView(view)
//                .setPositiveButton("Reset exam", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        Log.d("ResetExamDebug", "Reset exam button clicked");
//
//                        if (resetExamListener != null) {
//                            Log.d("ResetExamDebug", "Calling onResetExam");
//                            resetExamListener.onResetExam();
//                        } else {
//                            Log.e("ResetExamDebug", "resetExamListener is null");
//                        }
//
//                        if (adapter != null) {
//                            Log.d("ResetExamDebug", "Setting adapter restart to true");
//                            adapter.setRestart(true);
//                        } else {
//                            Log.e("ResetExamDebug", "Adapter is null");
//                        }
//                    }
//                })
//                .setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });

        builder.setView(view);

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

