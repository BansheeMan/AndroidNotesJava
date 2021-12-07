package com.example.androidnotesjava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentExit extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle("Выход")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton("Да", (d, i) -> {
                    Toast.makeText(activity, "Ваше приложение закрыто!", Toast.LENGTH_LONG).show();
                    activity.finish();
                })
                .setNeutralButton("Нет", null);
        Dialog answer = builder.create();
        return answer;
    }
}
