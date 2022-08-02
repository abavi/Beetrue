package com.example.beetrueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    ImageView notesVector, toDoListVector, pomodoroVector, habitsVector;
    Button btnSettings;
    TextView txtWelcomeBack;

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

        // Setting up on click listeners
        notesVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });
        toDoListVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ToDoListActivity.class);
                startActivity(intent);
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