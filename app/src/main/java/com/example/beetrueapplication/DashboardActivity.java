package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    DatabaseReference fUsersDatabase;
    FirebaseAuth fAuth;
    ImageView notesVector, toDoListVector, pomodoroVector, habitsVector;
    Button btnSettings;
    TextView txtWelcomeBack;
    String username;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialising all the views
        notesVector = findViewById(R.id.notesVector);
        toDoListVector = findViewById(R.id.toDoListVector);
        pomodoroVector = findViewById(R.id.pomodoroVector);
        habitsVector = findViewById(R.id.habitsVector);
        txtWelcomeBack = findViewById(R.id.txtWelcomeBack);
        btnSettings = findViewById(R.id.btnSettings);


        // Display username in welcome back message
        fAuth = FirebaseAuth.getInstance();
        if(fAuth != null){
            fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
            fUsersDatabase.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    username = snapshot.getValue(String.class);
                    txtWelcomeBack.setText("Welcome back " + username + " !");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        // Setting up on click listeners
        notesVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NotesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        toDoListVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ToDoListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        pomodoroVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
        habitsVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, HabitsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }
}