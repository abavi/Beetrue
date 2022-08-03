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

public class NewTaskActivity extends AppCompatActivity {

    private EditText etTaskContent;
    private FloatingActionButton fabSaveTask;

    private FirebaseAuth fAuth;
    private DatabaseReference fTasksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        etTaskContent = findViewById(R.id.etTaskContent);
        fabSaveTask = findViewById(R.id.fabSaveTask);

        fAuth = FirebaseAuth.getInstance();
        fTasksDatabase = FirebaseDatabase.getInstance().getReference().child("tasks").child(fAuth.getCurrentUser().getUid());

        fabSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskContent = etTaskContent.getText().toString().trim();


                if(!TextUtils.isEmpty(taskContent)){
                    String taskID = fTasksDatabase.push().getKey();
                    createTask(taskContent);
                }
                else {
                    Snackbar.make(v,"Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createTask(String taskContent){
        if(fAuth.getCurrentUser() != null ){
            // Create references for each note
            DatabaseReference newTaskRef = fTasksDatabase.push();
            String taskID = newTaskRef.getKey();
            TaskModel task = new TaskModel(taskContent, taskID);

            newTaskRef.setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(NewTaskActivity.this, "Task added to the database!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewTaskActivity.this, DashboardActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(NewTaskActivity.this, "Problem adding note" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "User is not signed in!", Toast.LENGTH_SHORT).show();
        }
    }
}
