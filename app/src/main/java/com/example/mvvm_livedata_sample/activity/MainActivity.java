package com.example.mvvm_livedata_sample.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_livedata_sample.R;
import com.example.mvvm_livedata_sample.adapter.NoteAdapter;
import com.example.mvvm_livedata_sample.entity.Note;
import com.example.mvvm_livedata_sample.view_model.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        try {
            recyclerView = findViewById(R.id.recMain);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            final NoteAdapter noteAdapter = new NoteAdapter();
            recyclerView.setAdapter(noteAdapter);

            noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
            noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    noteAdapter.setNotes(notes);
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
