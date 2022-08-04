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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditTaskActivity extends AppCompatActivity {

    EditText taskContent;
    String taskContentSend;
    private DatabaseReference fTasksReference;
    private TaskModel task;
    Button btnDeleteTask, btnUpdateTask;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        fAuth = FirebaseAuth.getInstance();

        btnDeleteTask = findViewById(R.id.btnDeleteEditedTask);
        btnUpdateTask = findViewById(R.id.btnSaveEditTask);

        final Intent i = getIntent();

        String getTaskContent = i.getStringExtra("content");
        final String taskID = i.getStringExtra("taskID");

        taskContent = findViewById(R.id.etEditTaskContent);

        taskContent.setText(getTaskContent);

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), taskID, Toast.LENGTH_SHORT).show();
                updateTasks(taskID);
                //finish();
            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask(taskID);
                //finish();
            }
        });
    }

    private void updateTasks(String taskID) {
        taskContentSend = taskContent.getText().toString();
        TaskModel task = new TaskModel(taskContentSend, taskID);
        fTasksReference = FirebaseDatabase.getInstance().getReference().child("tasks").child(fAuth.getCurrentUser().getUid());
        fTasksReference.child(taskID).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditTaskActivity.this, "Task updated!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
                finish();
            }
        });
    }

    private void deleteTask(String noteID) {

        fTasksReference = FirebaseDatabase.getInstance().getReference().child("tasks").child(fAuth.getCurrentUser().getUid());
        fTasksReference.child(noteID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditTaskActivity.this, "Task deleted!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
                finish();
            }
        });

    }
}
