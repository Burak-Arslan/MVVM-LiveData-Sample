package com.example.mvvm_livedata_sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_livedata_sample.R;
import com.example.mvvm_livedata_sample.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.txtPriotry.setText(currentNote.getPriority());
        holder.txtTitle.setText(currentNote.getTitle());
        holder.txtDesc.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtPriotry;
        private TextView txtDesc;

        public NoteHolder(View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPriotry = itemView.findViewById(R.id.txtPriority);
        }
    }
}
