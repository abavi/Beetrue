package com.example.beetrueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    Button btnLogOut, btnChangePassword, btnChangeEmail, btnChangeUsername;
    FirebaseAuth fAuth;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fAuth = FirebaseAuth.getInstance();

        btnLogOut = findViewById(R.id.btnLogOut);
        btnChangeEmail = findViewById(R.id.btnChangeEmail);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangeUsername = findViewById(R.id.btnChangeUsername);

        //Change user password
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
        //Change user email
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                startActivity(i);
                finish();
            }
        });
        //Change username
        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ChangeUsernameActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Sign user out
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fAuth != null){
                    fAuth.signOut();
                    Intent i = new Intent(SettingsActivity.this, LoginActivity.class); // Send user back to login activity
                    startActivity(i);
                    finish(); // Ensure on back press user is out of the application
                }

            }
        });

    }
}