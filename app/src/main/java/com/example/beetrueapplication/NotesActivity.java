package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;
    private RecyclerView notesList;
    ArrayList<Note> notes;
    NotesAdapter adapter;
    private FloatingActionButton fabCreateNewNote;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotesActivity.this, DashboardActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesList = findViewById(R.id.notesList);
        fabCreateNewNote = findViewById(R.id.fabCreateNewNote);
        notesList.setLayoutManager(new GridLayoutManager(this, 2));

        fabCreateNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, NewNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if(fAuth != null){
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("notes").child(fAuth.getCurrentUser().getUid());
            //Toast.makeText(this, fNotesDatabase.toString(), Toast.LENGTH_SHORT).show();;
            // Finds correct path
        }

        notes = new ArrayList<>();
        adapter = new NotesAdapter(this, notes);
        notesList.setAdapter(adapter);

        fNotesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Note note = dataSnapshot.getValue(Note.class);
                    notes.add(note);
                }
                adapter.notifyDataSetChanged();
            }
            

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



}
