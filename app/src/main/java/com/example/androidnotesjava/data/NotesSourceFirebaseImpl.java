package com.example.androidnotesjava.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesSourceFirebaseImpl implements NotesSource{
    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NotesSourceFirebaseImpl]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    private List<Note> notesData = new ArrayList<>();

    @Override
    public NotesSource init(NotesSourceResponse cardsSourceResponse) {
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    notesData = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> doc = document.getData();
                        String id = document.getId();
                        Note note = NoteMapping.toNote(id, doc);
                        notesData.add(note);
                    }
                    cardsSourceResponse.initialized(NotesSourceFirebaseImpl.this);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Note getNote(int position) {
        return notesData.get(position);
    }

    @Override
    public int size() {
        if (notesData == null) {
            return 0;
        }
        return notesData.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNote(int position, Note newNote) {
        String id = newNote.getId();
        collection.document(id).set(NoteMapping.toDocument(newNote));

    }

    @Override
    public void addNote(Note newNote) {

        collection.add(NoteMapping.toDocument(newNote)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                newNote.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNote() {
        for (Note note: notesData) {
            collection.document(note.getId()).delete();
        }
        notesData = new ArrayList<Note>();
    }
}

