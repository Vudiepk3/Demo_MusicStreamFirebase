package com.example.demo_musicstreamfirebase.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.demo_musicstreamfirebase.R;

public class SplashScreenActivity extends AppCompatActivity {
    // Biến lưu trữ đối tượng ImageView
    private ImageView zoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        zoomImageView = findViewById(R.id.zoomImageView); // Lấy đối tượng ImageView từ layout
        animateZoomOut(); // Bắt đầu hiệu ứng thu nhỏ ImageView
    }
    private void animateZoomOut() {
        zoomImageView.animate()
                .scaleX(0.4f) // Thu nhỏ trục X xuống còn 40% kích thước ban đầu
                .scaleY(0.4f) // Thu nhỏ trục Y xuống còn 40% kích thước ban đầu
                .setDuration(1500) // Đặt thời gian cho hiệu ứng là 1000 mili giây (1 giây)
                .withEndAction(this::animateZoomIn) // Sau khi hiệu ứng kết thúc, bắt đầu hiệu ứng phóng to
                .start(); // Bắt đầu hiệu ứng
    }

    // Phương thức này thực hiện hiệu ứng phóng to trên ImageView
    private void animateZoomIn() {
        zoomImageView.animate()
                .scaleX(30.0f) // Phóng to trục X lên 3000% kích thước ban đầu
                .scaleY(30.0f) // Phóng to trục Y lên 3000% kích thước ban đầu
                .setDuration(500) // Đặt thời gian cho hiệu ứng là 500 mili giây (0.5 giây)
                .withEndAction(this::startNewActivity) // Sau khi hiệu ứng kết thúc, bắt đầu activity mới
                .start(); // Bắt đầu hiệu ứng
    }
    private void startNewActivity() {
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class)); // Chuyển sang MainActivity
        finish(); // Đóng SplashScreenActivity để người dùng không thể quay lại màn hình này
    }
}
