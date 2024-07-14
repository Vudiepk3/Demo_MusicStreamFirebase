package com.example.demo_musicstreamfirebase.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.demo_musicstreamfirebase.adapters.SongsListAdapter;
import com.example.demo_musicstreamfirebase.databinding.ActivitySongListBinding;
import com.example.demo_musicstreamfirebase.models.CategoryModel;

public class SongListActivity extends AppCompatActivity {

    private static CategoryModel category;

    private ActivitySongListBinding binding;

    // Getter for category
    public static CategoryModel getCategory() {
        return category;
    }

    // Setter for category
    public static void setCategory(CategoryModel category) {
        SongListActivity.category = category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nameTextView.setText(category.getName());
        // Load cover image using Glide
        // ...

        setupSongsListRecyclerView();
    }

    private void setupSongsListRecyclerView() {
        SongsListAdapter songsListAdapter = new SongsListAdapter(category.getSongs());
        binding.songsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.songsListRecyclerView.setAdapter(songsListAdapter);
    }
}
