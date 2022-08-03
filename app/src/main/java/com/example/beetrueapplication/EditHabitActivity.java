package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditHabitActivity extends AppCompatActivity {

    EditText habitContent;
    TextView selectDate;
    String habitContentSend, dateSend;
    private DatabaseReference fHabitsReference;
    private HabitModel habit;
    Button btnDeleteHabit, btnUpdateHabit;
    private FirebaseAuth fAuth;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);

        fAuth = FirebaseAuth.getInstance();

        btnDeleteHabit = findViewById(R.id.btnDeleteEditedHabit);
        btnUpdateHabit = findViewById(R.id.btnSaveEditHabit);

        final Intent i = getIntent();

        String getHabitContent = i.getStringExtra("content");
        String getHabitDate = i.getStringExtra("date");
        final String habitID = i.getStringExtra("habitID");

        habitContent = findViewById(R.id.etEditHabitContent);
        selectDate = findViewById(R.id.etEditSelectDate);

        selectDate.setText(getHabitDate);
        habitContent.setText(getHabitContent);

        // Allow users to change the date
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date Picker Dialog
                picker = new DatePickerDialog(EditHabitActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
            }
        });

        btnUpdateHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), taskID, Toast.LENGTH_SHORT).show();
                updateHabit(habitID);
                finish();
            }
        });

        btnDeleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHabit(habitID);
                finish();
            }
        });
    }

    private void updateHabit(String habitID) {
        habitContentSend = habitContent.getText().toString();
        dateSend = selectDate.getText().toString();
        HabitModel habit = new HabitModel(habitContentSend, dateSend, habitID);
        fHabitsReference = FirebaseDatabase.getInstance().getReference().child("habits").child(fAuth.getCurrentUser().getUid());
        fHabitsReference.child(habitID).setValue(habit).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditHabitActivity.this, "Habit updated!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HabitsActivity.class));
                finish();
            }
        });
    }

    private void deleteHabit(String habitID) {

        fHabitsReference = FirebaseDatabase.getInstance().getReference().child("habits").child(fAuth.getCurrentUser().getUid());
        fHabitsReference.child(habitID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditHabitActivity.this, "Habit deleted!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HabitsActivity.class));
                finish();
            }
        });

    }
}