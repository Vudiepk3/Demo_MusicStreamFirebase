package com.example.demo_musicstreamfirebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.demo_musicstreamfirebase.R;
import com.example.demo_musicstreamfirebase.models.MyExoplayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.demo_musicstreamfirebase.adapters.CategoryAdapter;
import com.example.demo_musicstreamfirebase.adapters.SectionSongListAdapter;
import com.example.demo_musicstreamfirebase.databinding.ActivityMainBinding;
import com.example.demo_musicstreamfirebase.models.CategoryModel;
import com.example.demo_musicstreamfirebase.models.SongModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getCategories();
        setupSection("section_1", binding.section1MainLayout, binding.section1Title, binding.section1RecyclerView);
        setupSection("section_2", binding.section2MainLayout, binding.section2Title, binding.section2RecyclerView);
        setupSection("section_3", binding.section3MainLayout, binding.section3Title, binding.section3RecyclerView);
        setupMostlyPlayed(binding.mostlyPlayedMainLayout, binding.mostlyPlayedTitle, binding.mostlyPlayedRecyclerView);
        binding.optionBtn.setOnClickListener(v -> showPopupMenu());
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, binding.optionBtn);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logout) {
                logout();
                return true;
            }
            return false;
        });
    }

    private void logout() {
        MyExoplayer.getInstance().release();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPlayerView();
    }

    private void showPlayerView() {
        binding.playerView.setOnClickListener(v -> startActivity(new Intent(this, PlayerActivity.class)));
        SongModel currentSong = MyExoplayer.getCurrentSong();
        if (currentSong != null) {
            binding.playerView.setVisibility(View.VISIBLE);
            String title = currentSong.getTitle();
            binding.songTitleTextView.setText("Now Playing: " + title);
            Glide.with(binding.songCoverImageView.getContext())
                    .load(currentSong.getCoverUrl())
                    .apply(new RequestOptions().transform(new RoundedCorners(32)))
                    .into(binding.songCoverImageView);
        } else {
            binding.playerView.setVisibility(View.GONE);
        }
    }

    // Fetching categories
    private void getCategories() {
        FirebaseFirestore.getInstance().collection("category")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<CategoryModel> categoryList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        categoryList.add(document.toObject(CategoryModel.class));
                    }
                    setupCategoryRecyclerView(categoryList);
                });
    }

    private void setupCategoryRecyclerView(List<CategoryModel> categoryList) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
        binding.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    // Setting up sections
    private void setupSection(String id, RelativeLayout mainLayout, TextView titleView, RecyclerView recyclerView) {
        FirebaseFirestore.getInstance().collection("sections")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    CategoryModel section = documentSnapshot.toObject(CategoryModel.class);
                    if (section != null) {
                        mainLayout.setVisibility(View.VISIBLE);
                        titleView.setText(section.getName());
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(new SectionSongListAdapter(section.getSongs()));
                        mainLayout.setOnClickListener(v -> {
                            SongListActivity.setCategory(section);
                            startActivity(new Intent(MainActivity.this, SongListActivity.class));
                        });
                    }
                });
    }

    private void setupMostlyPlayed(RelativeLayout mainLayout, TextView titleView, RecyclerView recyclerView) {
        FirebaseFirestore.getInstance().collection("sections")
                .document("mostly_played")
                .get()
                .addOnSuccessListener(documentSnapshot ->
                        FirebaseFirestore.getInstance().collection("songs")
                                .orderBy("count", Query.Direction.DESCENDING)
                                .limit(5)
                                .get()
                                .addOnSuccessListener(songListSnapshot -> {
                                    List<SongModel> songsModelList = songListSnapshot.toObjects(SongModel.class);
                                    List<String> songsIdList = new ArrayList<>();
                                    for (SongModel song : songsModelList) {
                                        songsIdList.add(song.getId());
                                    }
                                    CategoryModel section = documentSnapshot.toObject(CategoryModel.class);
                                    if (section != null) {
                                        section.setSongs(songsIdList);
                                        mainLayout.setVisibility(View.VISIBLE);
                                        titleView.setText(section.getName());
                                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                                        recyclerView.setAdapter(new SectionSongListAdapter(section.getSongs()));
                                        mainLayout.setOnClickListener(v -> {
                                            SongListActivity.setCategory(section);
                                            startActivity(new Intent(MainActivity.this, SongListActivity.class));
                                        });
                                    }
                                })
                );
    }
}
