package com.example.androidnotesjava.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidnotesjava.MainActivity;
import com.example.androidnotesjava.R;

public class DialogFragmentExit extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) requireActivity();
        return new AlertDialog.Builder(activity)
                .setTitle("Выход")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.exit_sure)
                .setPositiveButton(R.string.yes, (d, i) -> {
                    Toast.makeText(activity, R.string.app_is_closed, Toast.LENGTH_LONG).show();
                    activity.finish();
                })
                .setNeutralButton(R.string.no, null).create();
    }
}
