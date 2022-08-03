package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText etNewEmail;
    Button btnSaveNewEmail;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        etNewEmail = findViewById(R.id.etNewEmail);
        btnSaveNewEmail = findViewById(R.id.btnSaveEditEmail);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        btnSaveNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    String newEmail = etNewEmail.getText().toString().trim();
                    //Validate email
                    boolean isValidated = validateEmail(newEmail);
                    if(!isValidated){
                        return;
                    }
                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChangeEmailActivity.this, "Email changed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(ChangeEmailActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });



    }

    private boolean validateEmail(String newEmail){
        if(newEmail.isEmpty()) {
            etNewEmail.setError("Email Required!");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            etNewEmail.setError("Provide valid email address!");
            return false;
        }
        return true;
    }
}