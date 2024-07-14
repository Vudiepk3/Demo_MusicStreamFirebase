package com.example.demo_musicstreamfirebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.demo_musicstreamfirebase.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập sự kiện click cho nút đăng nhập
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailEdittext.getText().toString();
            String password = binding.passwordEdittext.getText().toString();

            // Kiểm tra email có hợp lệ không
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEdittext.setError("Invalid email");
                return;
            }

            // Kiểm tra độ dài của mật khẩu
            if (password.length() < 6) {
                binding.passwordEdittext.setError("Length should be 6 char");
                return;
            }
            // Đăng nhập với Firebase
            loginWithFirebase(email, password);
        });

        // Thiết lập sự kiện click cho nút chuyển sang đăng ký
        binding.gotoSignupBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }

    // Phương thức đăng nhập với Firebase
    private void loginWithFirebase(String email, String password) {
        setInProgress(true);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    setInProgress(false);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    setInProgress(false);
                    Toast.makeText(getApplicationContext(), "Login account failed", Toast.LENGTH_SHORT).show();
                });
    }

    // Phương thức thiết lập trạng thái tiến trình
    private void setInProgress(boolean inProgress) {
        if (inProgress) {
            binding.loginBtn.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.loginBtn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}
