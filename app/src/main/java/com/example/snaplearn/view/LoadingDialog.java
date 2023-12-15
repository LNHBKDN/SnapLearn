package com.example.snaplearn.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.snaplearn.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog dialog;
    LoadingDialog(Login myActivity) {
        activity = myActivity;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));

        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    void closeLoadingDialog() {
        dialog.dismiss();
    }
}
