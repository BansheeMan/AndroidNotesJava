package com.example.androidnotesjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {

    public static String ARG_NOTE = "note";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        noteFragment.setArguments(bundle);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.note = getArguments().getParcelable(ARG_NOTE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        String str_date = (getResources().getString(R.string.date)) + this.note.getDate();
        TextView textView1 = view.findViewById(R.id.textView_date);
        textView1.setText(str_date);

        String str_name = (getResources().getString(R.string.name)) + this.note.getName();
        TextView textView2 = view.findViewById(R.id.textView_name);
        textView2.setText(str_name);

        String str_description = (getResources().getString(R.string.description)) + this.note.getDescription();
        TextView textView3 = view.findViewById(R.id.textView_description);
        textView3.setText(str_description);
        return view;
    }
}