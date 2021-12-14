package com.example.androidnotesjava.data;

import android.content.res.Resources;

import com.example.androidnotesjava.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotesSourceImp implements NotesSource {

    private List<Note> dataSource;
    private Resources resource;

    public NotesSourceImp(Resources resource) {
        this.dataSource = new ArrayList<>(12);
        this.resource = resource;
    }

    public NotesSourceImp init() {
        String[] titles = resource.getStringArray(R.array.titles);
        String[] descriptions = resource.getStringArray(R.array.description);
        for (int i = 0; i < descriptions.length; i++){
            dataSource.add(new Note(titles[i], descriptions[i], Calendar.getInstance().getTime()));
        }
        return this;
    }

    @Override
    public Note getNote(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteNote(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note newNote) {
        dataSource.set(position, newNote);
    }

    @Override
    public void addNote(Note newNote) {
        dataSource.add(newNote);
    }

    @Override
    public void clearNote() {
        dataSource.clear();
    }
}
