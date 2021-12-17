package com.example.androidnotesjava.data;

public interface NotesSourceResponse {

    void initialized(NotesSource notesSource);

    public interface CardsSourceResponse {
        void initialized(NotesSource notesData);
    }
}
