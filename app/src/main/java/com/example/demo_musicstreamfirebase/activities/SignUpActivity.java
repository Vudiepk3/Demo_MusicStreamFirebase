package com.example.demo_musicstreamfirebase.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.demo_musicstreamfirebase.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createAccountBtn.setOnClickListener(view -> {

            String email = binding.emailEdittext.getText().toString();
            String password = binding.passwordEdittext.getText().toString();
            String confirmPassword = binding.confirmPasswordEdittext.getText().toString();

            // Kiểm tra email hợp lệ
            if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)) {
                binding.emailEdittext.setError("Invalid email");
                return;
            }

            // Kiểm tra độ dài mật khẩu
            if (password.length() < 6) {
                binding.passwordEdittext.setError("Length should be 6 char");
                return;
            }

            // Kiểm tra mật khẩu khớp nhau
            if (!password.equals(confirmPassword)) {
                binding.confirmPasswordEdittext.setError("Password not matched");
                return;
            }

            // Tạo tài khoản với Firebase
            createAccountWithFirebase(email, password);
        });

        // Chuyển tới màn hình đăng nhập
        binding.gotoLoginBtn.setOnClickListener(view -> finish());
    }

    // Tạo tài khoản với Firebase
    private void createAccountWithFirebase(String email, String password) {
        setInProgress(true);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    setInProgress(false);
                    Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    setInProgress(false);
                    Toast.makeText(getApplicationContext(), "Create account failed", Toast.LENGTH_SHORT).show();
                });
    }

    // Hiển thị tiến trình
    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            binding.createAccountBtn.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.createAccountBtn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}
