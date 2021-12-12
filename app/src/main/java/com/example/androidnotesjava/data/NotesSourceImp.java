package com.example.androidnotesjava.data;

import android.content.res.Resources;

import com.example.androidnotesjava.R;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImp implements NotesSource {

    private List<Note> dataSource;
    private Resources resource;

    public NotesSourceImp(Resources resource) {
        this.dataSource = new ArrayList<>(12);
        this.resource = resource;
    }


    public NotesSourceImp init() {
        String[] names = resource.getStringArray(R.array.names);
        String[] descriptions = resource.getStringArray(R.array.description);
        String[] dates = resource.getStringArray(R.array.dates);
        for (int i = 0; i < descriptions.length; i++){
            dataSource.add(new Note(names[i], descriptions[i], dates[i]));
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
}
