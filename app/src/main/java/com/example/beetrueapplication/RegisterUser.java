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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    EditText etFirstName, etLastName, etUsername, etPassword, etEmailAddress, etConfirmPassword;
    Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        // Take user back to login if he already has an account
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void createAccount(){
        String email = etEmailAddress.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean isValidated = validateData(email, firstName, lastName, username, password, confirmPassword);
        if(!isValidated){
            return;
        }
        createAccountInFirebase(email,password, firstName, lastName, username);
    }

    private boolean validateData(String email, String firstName, String lastName, String username, String password, String confirmPassword){
        if(firstName.isEmpty()) {
            etFirstName.setError("First Name Required!");
            return false;
        }
        if(lastName.isEmpty()) {
            etLastName.setError("Last Name Required!");
            return false;
        }
        if(email.isEmpty()) {
            etEmailAddress.setError("Email Required!");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailAddress.setError("Provide valid email address!");
            return false;
        }
        if(username.isEmpty()) {
            etUsername.setError("Username Required!");
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

        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Passwords do not match!");
            return false;
        }
        return true;
    }

    private void createAccountInFirebase(String email, String password, String firstName,
                                         String lastName, String username){
        // Creating new user details to be stored in the realtime database
        User user = new User(firstName, lastName, username);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterUser.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Account creation successful
                            Toast.makeText(RegisterUser.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                            mDatabase.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(user); // Store the users extra information besides email and password
                            firebaseAuth.signOut();
                            finish();
                        }
                        else {
                            // Failure creation
                            Toast.makeText(RegisterUser.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}