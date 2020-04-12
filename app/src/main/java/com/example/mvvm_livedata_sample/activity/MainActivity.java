package com.example.mvvm_livedata_sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_livedata_sample.R;
import com.example.mvvm_livedata_sample.adapter.NoteAdapter;
import com.example.mvvm_livedata_sample.entity.Note;
import com.example.mvvm_livedata_sample.view_model.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    private NoteViewModel noteViewModel;
    private FloatingActionButton fltBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        events();
    }

    private void events() {
        try {
            fltBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                    startActivityForResult(intent, ADD_NOTE_REQUEST);
                }
            });

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Silindi", Toast.LENGTH_SHORT).show();
                }
            }).attachToRecyclerView(recyclerView);

            noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
                @Override
                public void onItemClick(Note note) {
                    Intent intentEditNote = new Intent(MainActivity.this, AddNoteActivity.class);
                    intentEditNote.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
                    intentEditNote.putExtra(AddNoteActivity.EXTRA_ID, note.getId());
                    intentEditNote.putExtra(AddNoteActivity.EXTRA_DESC, note.getDescription());
                    intentEditNote.putExtra(AddNoteActivity.EXTRA_PRIORITY, note.getPriority());
                    startActivityForResult(intentEditNote, EDIT_NOTE_REQUEST);
                }
            });

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        try {
            recyclerView = findViewById(R.id.recMain);
            fltBtnAdd = findViewById(R.id.fltBtnAdd);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            noteAdapter = new NoteAdapter();
            recyclerView.setAdapter(noteAdapter);

            noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
            noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    noteAdapter.submitList(notes);
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_DESC);
            String prio = data.getStringExtra(AddNoteActivity.EXTRA_PRIORITY);

            Note note = new Note(title, desc, prio);
            noteViewModel.insert(note);
            Toast.makeText(this, "Kayıt Edildi", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Kayıt Edilmedi", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_DESC);
            String prio = data.getStringExtra(AddNoteActivity.EXTRA_PRIORITY);

            Note note = new Note(title, desc, prio);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Kayıt Güncellendi", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Kayıt Edilmedi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "Alayını sildik attık", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
