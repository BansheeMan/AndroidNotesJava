package com.example.androidnotesjava.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotesjava.MainActivity;
import com.example.androidnotesjava.Navigation;
import com.example.androidnotesjava.data.Note;
import com.example.androidnotesjava.R;
import com.example.androidnotesjava.data.NotesSource;
import com.example.androidnotesjava.data.NotesSourceFirebaseImpl;
import com.example.androidnotesjava.data.NotesSourceImp;
import com.example.androidnotesjava.data.NotesSourceResponse;
import com.example.androidnotesjava.observe.Observer;
import com.example.androidnotesjava.observe.Publisher;


public class ListNotesFragment extends Fragment {

    private NotesSource data;
    private ListNotesAdapter adapter;
    private RecyclerView recyclerView;

    private Navigation navigation;
    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }
    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_lines);
        initRecyclerView(recyclerView, data);
        data = new NotesSourceFirebaseImpl().init(new NotesSourceResponse() {
            @Override
            public void initialized(NotesSource notesSource) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    private void initRecyclerView (RecyclerView recyclerView, NotesSource data){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListNotesAdapter(this);
        recyclerView.setAdapter(adapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(2000);
        defaultItemAnimator.setChangeDuration(2000);
        defaultItemAnimator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(defaultItemAnimator);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.SetOnItemClickListener((view, position) -> {
            Toast.makeText(getContext(), String.format("Закладка № - %d", position), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                navigation.addFragment(NoteUpdateFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        data.addNote(note);
                        adapter.notifyItemInserted(data.size() - 1);
                    }
                });
                return true;
            case R.id.action_clear:
                data.clearNote();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.note_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuContextClickPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(NoteUpdateFragment.newInstance(data.getNote(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        data.updateNote(position,note);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}