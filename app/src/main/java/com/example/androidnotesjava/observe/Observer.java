package com.example.androidnotesjava.observe;

import com.example.androidnotesjava.data.Note;

public interface Observer {
    void updateState(Note note);
}
