package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNoteActivity extends AppCompatActivity {

    EditText noteTitle, noteContent;
    String noteTitleSend, noteContentSend;
    private DatabaseReference fNoteReference;
    private Note note;
    Button btnDeleteNote, btnUpdateNote;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        fAuth = FirebaseAuth.getInstance();

        btnDeleteNote = findViewById(R.id.btnDeleteEditedNote);
        btnUpdateNote = findViewById(R.id.btnSaveEditNote);

        final Intent i = getIntent();

        String getNoteTitle = i.getStringExtra("title");
        String getNoteContent = i.getStringExtra("content");
        final String noteID = i.getStringExtra("noteID");

        noteTitle = findViewById(R.id.etEditNoteTitle);
        noteContent = findViewById(R.id.etEditNoteContent);

        noteTitle.setText(getNoteTitle);
        noteContent.setText(getNoteContent);

        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), noteID, Toast.LENGTH_SHORT).show();
                updateNotes(noteID);
            }
        });
        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), noteID, Toast.LENGTH_SHORT).show();
                deleteNote(noteID);
            }
        });
    }

    private void updateNotes(String noteID){
        noteTitleSend = noteTitle.getText().toString();
        noteContentSend = noteContent.getText().toString();
        Note note = new Note(noteTitleSend, noteContentSend, noteID);
        fNoteReference = FirebaseDatabase.getInstance().getReference().child("notes").child(fAuth.getCurrentUser().getUid());
        fNoteReference.child(noteID).setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditNoteActivity.this, "Note updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                finish();
            }
        });


    }

    private void deleteNote(String noteID){

        fNoteReference = FirebaseDatabase.getInstance().getReference().child("notes").child(fAuth.getCurrentUser().getUid());
        fNoteReference.child(noteID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditNoteActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                finish();
            }
        });


    }

}