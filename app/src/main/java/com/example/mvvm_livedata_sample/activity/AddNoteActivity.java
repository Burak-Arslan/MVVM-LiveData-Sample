package com.example.mvvm_livedata_sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvm_livedata_sample.R;


public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.mvvm_livedata_sample.activity.EXTRA_TITLE";
    public static final String EXTRA_ID = "com.example.mvvm_livedata_sample.activity.EXTRA_ID";
    public static final String EXTRA_DESC = "com.example.mvvm_livedata_sample.activity.EXTRA_DESC";
    public static final String EXTRA_PRIORITY = "com.example.mvvm_livedata_sample.activity.EXTRA_PRIO";

    private EditText edtTitle;
    private EditText edtDesc;
    private NumberPicker numberPickerPriotiy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        init();
    }

    private void init() {
        try {
            edtTitle = findViewById(R.id.edtTitle);
            edtDesc = findViewById(R.id.edtDesc);
            numberPickerPriotiy = findViewById(R.id.number);
            numberPickerPriotiy.setMinValue(1);
            numberPickerPriotiy.setMaxValue(20);
            numberPickerPriotiy.setWrapSelectorWheel(false);
            numberPickerPriotiy.setValue(1);

            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_ID)) {
                setTitle("EditNote");
                edtTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                edtDesc.setText(intent.getStringExtra(EXTRA_DESC));
                numberPickerPriotiy.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            } else {
                setTitle("Add Note");
            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSaveNote:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        try {
            String title = edtTitle.getText().toString();
            String desc = edtDesc.getText().toString();
            int priority = numberPickerPriotiy.getValue();

            if (title.trim().isEmpty() || desc.trim().isEmpty()) {
                Toast.makeText(this, "Title ve Desc giriniz!", Toast.LENGTH_SHORT).show();
            }

            Intent intentData = new Intent();
            intentData.putExtra(EXTRA_TITLE, title);
            intentData.putExtra(EXTRA_DESC, desc);
            intentData.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                intentData.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, intentData);
            finish();

        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
