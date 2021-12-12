package com.example.androidnotesjava.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnotesjava.R;
import com.example.androidnotesjava.data.Note;
import com.example.androidnotesjava.data.NotesSource;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.MyViewHolder> {

    private NotesSource dataSource;
    private OnItemClickListener itemClickListener;

    public ListNotesAdapter(NotesSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ListNotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotesAdapter.MyViewHolder holder, int position) {
        holder.setData(dataSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_name);
            description = itemView.findViewById(R.id.textView_description);
            date = itemView.findViewById(R.id.textView_date);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Note noteData) {
            name.setText(noteData.getName());
            description.setText(noteData.getDescription());
            date.setText(noteData.getDate());
        }
    }
}
