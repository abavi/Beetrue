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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText etNewPassword;
    Button btnSaveNewPassword;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etNewPassword = findViewById(R.id.etNewPassword);
        btnSaveNewPassword = findViewById(R.id.btnSaveEditPassword);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        // Update password
        btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null){
                    String newPassword = etNewPassword.getText().toString().trim();
                    //Validate password
                    boolean isValidated =validatePassword(newPassword);
                    if(!isValidated){
                        return;
                    }
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private boolean validatePassword(String password){
        if(password.isEmpty()) {
            etNewPassword.setError("Password Required!");
            return false;
        }
        if (password.length() < 6) {
            etNewPassword.setError("Password length should be more than 5 characters!");
            return false;
        }
        return true;
    }
}