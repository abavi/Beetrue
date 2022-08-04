package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeUsernameActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText etNewUsername;
    Button btnSaveNewUsername;
    DatabaseReference fUsersDatabase;
    String username;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChangeUsernameActivity.this, SettingsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username_acivity);

        etNewUsername = findViewById(R.id.etNewUsername);
        btnSaveNewUsername = findViewById(R.id.btnSaveEditUsername);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(fAuth.getCurrentUser().getUid());
            btnSaveNewUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username = etNewUsername.getText().toString().trim();

                    boolean isValidated = validateUsername(username);
                    if(!isValidated){
                        return;
                    }

                    fUsersDatabase.child("username").setValue(username);
                    Toast.makeText(ChangeUsernameActivity.this, "Username updated successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangeUsernameActivity.this, DashboardActivity.class);
//                    finish();
                }
            });
        }

    }

    private boolean validateUsername(String username){
        if(username.isEmpty()) {
            etNewUsername.setError("Username Required!");
            return false;
        }
        return true;
    }


}