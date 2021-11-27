package com.example.appcat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    Button BtnSignUp;
    EditText UserName, password, email;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.inputEmail);
        UserName = findViewById(R.id.inputUserName);
        password = findViewById(R.id.inputPassword);
        Button BtnLogin = findViewById(R.id.btnLogIn);
        mAuth = FirebaseAuth.getInstance();

        //set Onclick listener for our login button
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    email.setError("Enter Email");
                }
                if (TextUtils.isEmpty(Password)){
                    password.setError("Enter Password");
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                    return;
                }
                if (Password.length()<6){
                    password.setError("Password must be more than 6 characters");
                    password.requestFocus();
                    return;
                }

                //logging in the user
                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        startActivity(new Intent(LogInActivity.this, Dashboard.class));
                    }
                    else {
                        Toast.makeText(LogInActivity.this, "Please check your Login credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        TextView btn = findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

    }
}