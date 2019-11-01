package com.example.rapido.utils;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialog;


import com.example.rapido.databinding.DialogProgressBinding;

import java.util.Objects;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class LoadingDialog extends AppCompatDialog {

    private DialogProgressBinding dialogProgressBinding;

    public LoadingDialog(Context context) {
        super(context);
        dialogProgressBinding = DialogProgressBinding.inflate(LayoutInflater.from(context));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogProgressBinding.getRoot());
        Objects.requireNonNull(getWindow()).setLayout(MATCH_PARENT, WRAP_CONTENT);
    }

    public void setMessage(String message) {
        dialogProgressBinding.txtMessage.setText(message);
    }
}
