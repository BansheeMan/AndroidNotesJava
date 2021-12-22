package com.example.androidnotesjava.data;

public interface NotesSource {

    Note getNote(int position);
    int size();
}
