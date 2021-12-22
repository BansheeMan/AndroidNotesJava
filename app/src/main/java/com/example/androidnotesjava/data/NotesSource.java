package com.example.androidnotesjava.data;

public interface NotesSource {

    Note getNote(int position);
    int size();

    void deleteNote(int position);
    void updateNote(int position, Note newNote);
    void addNote(Note newNote);
    void clearNote();

}
