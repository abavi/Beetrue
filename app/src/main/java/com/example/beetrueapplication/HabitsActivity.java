package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HabitsActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference fHabitDatabase;
    private RecyclerView habitsRecyclerView;
    ArrayList<HabitModel> habits;
    HabitsAdapter adapter;

    private FloatingActionButton fabCreateHabit;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HabitsActivity.this, DashboardActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        habitsRecyclerView = findViewById(R.id.habitsList);
        fabCreateHabit = findViewById(R.id.fabCreateNewHabit);
        habitsRecyclerView.setLayoutManager((new LinearLayoutManager(this)));

        fabCreateHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitsActivity.this, NewHabitActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if(fAuth != null){
            fHabitDatabase = FirebaseDatabase.getInstance().getReference().child("habits").child(fAuth.getCurrentUser().getUid());

        }

        habits = new ArrayList<>();
        adapter = new HabitsAdapter(this, habits);
        habitsRecyclerView.setAdapter(adapter);

        fHabitDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HabitModel habit = dataSnapshot.getValue(HabitModel.class);
                    habits.add(habit);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}