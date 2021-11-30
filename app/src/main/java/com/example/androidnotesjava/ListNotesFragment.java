package com.example.androidnotesjava;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.text.MessageFormat;

public class ListNotesFragment extends Fragment {

    private Note currentNote;
    public static String KEY_NOTE = "note";
    boolean isLandScape;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(KEY_NOTE);
        }
        if (isLandScape)
            if (currentNote != null) {
                showNote(currentNote.getNumber());
            } else {
                showNote(0);
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        LinearLayout linearLayout = (LinearLayout) view;

        String[] notes = getResources().getStringArray(R.array.names);

        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView textView = new TextView(getContext());
            String title = getResources().getString(R.string.title_note);
            textView.setText(MessageFormat.format("{0}{1} {2}", title, i + 1, note));
            textView.setTextSize(27);
            linearLayout.addView(textView);
            int finali = i;
            textView.setOnClickListener(view1 -> showNote(finali));
        }
        return view;
    }

    private void showNote(int index) {
        currentNote = new Note(index, (getResources().getStringArray(R.array.dates)[index]),
                (getResources().getStringArray(R.array.names)[index]),
                (getResources().getStringArray(R.array.description)[index]));
        if (isLandScape) {
            showNoteLand();
        } else {
            showNotePort();
        }
    }

    private void showNoteLand() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_container, NoteFragment.newInstance(currentNote))
                .commit();
    }

    private void showNotePort() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_notes_container, NoteFragment.newInstance(currentNote))
                .addToBackStack("")
                .commit();
    }
}