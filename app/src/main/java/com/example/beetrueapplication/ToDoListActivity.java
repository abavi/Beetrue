package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class ToDoListActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private DatabaseReference fTasksDatabase;
    private RecyclerView taskRecyclerView;
    ArrayList<TaskModel> tasks;
    ToDoAdapter adapter;

    FloatingActionButton fabCreateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        taskRecyclerView = findViewById(R.id.todoList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabCreateTask = findViewById(R.id.fabCreateTask);

        fabCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, NewTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if(fAuth != null){
            fTasksDatabase = FirebaseDatabase.getInstance().getReference().child("tasks").child(fAuth.getCurrentUser().getUid());
            //Toast.makeText(this, fNotesDatabase.toString(), Toast.LENGTH_SHORT).show();;
            // Finds correct path
        }

        tasks = new ArrayList<>();
        adapter = new ToDoAdapter(this, tasks);
        taskRecyclerView.setAdapter(adapter);

        fTasksDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TaskModel task = dataSnapshot.getValue(TaskModel.class);
                    tasks.add(task);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}