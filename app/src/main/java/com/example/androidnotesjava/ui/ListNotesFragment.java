package com.example.androidnotesjava.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotesjava.data.Note;
import com.example.androidnotesjava.R;
import com.example.androidnotesjava.data.NotesSource;
import com.example.androidnotesjava.data.NotesSourceImp;

import java.text.MessageFormat;

public class ListNotesFragment extends Fragment {


    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_lines);

        NotesSource data = new NotesSourceImp(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NotesSource data){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final ListNotesAdapter adapter = new ListNotesAdapter(data);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener((view, position) -> {
            Toast.makeText(getContext(), String.format("Закладка № - %d", position), Toast.LENGTH_SHORT).show();
        });
    }
}