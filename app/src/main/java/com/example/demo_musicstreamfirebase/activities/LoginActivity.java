package com.example.demo_musicstreamfirebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demo_musicstreamfirebase.R;

public class LoginActivity extends AppCompatActivity {
   private Button loginBtn;
   private TextView gotoSignupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

    }
    private void getID(){
        loginBtn = findViewById(R.id.login_btn);
        gotoSignupBtn = findViewById(R.id.goto_signup_btn);
    }
    private void setListener(){
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        });
        gotoSignupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        });
    }
}