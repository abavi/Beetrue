package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewNoteActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteConent;
    private FloatingActionButton fabSaveNote;

    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteConent = findViewById(R.id.etNoteContent);
        fabSaveNote = findViewById(R.id.fabSaveNote);

        fAuth = FirebaseAuth.getInstance();
        fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("notes").child(fAuth.getCurrentUser().getUid());

        fabSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTitle = etNoteTitle.getText().toString().trim();
                String noteContent = etNoteConent.getText().toString().trim();

                if(!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteContent)){
                    String noteID = fNotesDatabase.push().getKey();
                    createNote(noteTitle, noteContent);
                }
                else {
                    Snackbar.make(v,"Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createNote(String noteTitle, String noteContent){
        if(fAuth.getCurrentUser() != null ){

            // Create references for each note
            DatabaseReference newNoteRef = fNotesDatabase.push();
            String noteID = newNoteRef.getKey();
            Note note = new Note(noteTitle, noteContent, noteID);

            newNoteRef.setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(NewNoteActivity.this, "Note added to the database!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewNoteActivity.this, DashboardActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(NewNoteActivity.this, "Problem adding note" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "User is not signed in!", Toast.LENGTH_SHORT).show();
        }
    }
}