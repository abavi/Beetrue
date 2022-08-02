package com.example.beetrueapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmailAddress, etPassword;
    Button btnLogin,btnSignup;
    TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnLogin = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnSignUp);

        // Login user on button press
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        // Send user to the register activity on button press
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterUser.class);
                startActivity(intent);
            }
        });


    }

    private void loginUser(){
        String email = etEmailAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValidated = validateData(email, password);

        if(!isValidated){
            return;
        }

        loginAccountInFirebase(email, password);

    }

    private boolean validateData(String email, String password){
        if(email.isEmpty()) {
            etEmailAddress.setError("Email Required!");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailAddress.setError("Provide valid email address!");
            return false;
        }
        if(password.isEmpty()) {
            etPassword.setError("Password Required!");
            return false;
        }
        if (password.length() < 6) {
            etPassword.setError("Password length should be more than 5 characters!");
            return false;
        }
        return true;
    }

    private void loginAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Login success take user to dashboard
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    // Login failure, provide toast notification and restrict access
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}