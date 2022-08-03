package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewHabitActivity extends AppCompatActivity {

    EditText etHabitContent, etSelectDate;
    FloatingActionButton fabSaveHabit;
    DatePickerDialog picker;

    private FirebaseAuth fAuth;
    private DatabaseReference fHabitsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        etHabitContent = findViewById(R.id.etHabitContent);
        fabSaveHabit = findViewById(R.id.fabSaveHabit);
        etSelectDate = findViewById(R.id.etSelectDate);

        fAuth = FirebaseAuth.getInstance();
        fHabitsDatabase = FirebaseDatabase.getInstance().getReference().child("habits").child(fAuth.getCurrentUser().getUid());

        // Datepicker setup
        etSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date Picker Dialog
                picker = new DatePickerDialog(NewHabitActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etSelectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });

        fabSaveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitContent = etHabitContent.getText().toString().trim();
                String habitDate = etSelectDate.getText().toString().trim();


                if(!TextUtils.isEmpty(habitContent)){
                    String taskID = fHabitsDatabase.push().getKey();
                    createHabit(habitContent, habitDate);
                }
                else {
                    Snackbar.make(v,"Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createHabit(String habitContent, String habitDate){
        if(fAuth.getCurrentUser() != null ){
            // Create references for each note
            DatabaseReference newHabitRef = fHabitsDatabase.push();
            String habitID = newHabitRef.getKey();
            HabitModel habit = new HabitModel(habitContent, habitDate, habitID);

            newHabitRef.setValue(habit).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(NewHabitActivity.this, "Habit added to the database!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewHabitActivity.this, DashboardActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(NewHabitActivity.this, "Problem adding habit" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "User is not signed in!", Toast.LENGTH_SHORT).show();
        }
    }
}