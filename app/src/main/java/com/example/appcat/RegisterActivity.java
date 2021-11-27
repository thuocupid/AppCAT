package com.example.appcat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    Button BtnSignUp;
    EditText UserName, password, email;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //declaring the variables
        UserName = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);
        email = findViewById(R.id.inputEmail);
        BtnSignUp = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        //set onclick listener for the register button
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UsrName = UserName.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Email = email.getText().toString().trim();

                //making sure all fields are filled
                if (TextUtils.isEmpty(UsrName)){
                    UserName.setError("Enter Username");
                } else if (TextUtils.isEmpty(Password)){
                    password.setError("Enter password");
                } else if (Password.length()<6){
                    password.setError("Password must be more than 6 characters long");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Enter valid email address");
                    email.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "You have successfully registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //set Onclick listener for text
        TextView btn = findViewById(R.id.alreadyHaveAccount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
            }
        });

    }
}