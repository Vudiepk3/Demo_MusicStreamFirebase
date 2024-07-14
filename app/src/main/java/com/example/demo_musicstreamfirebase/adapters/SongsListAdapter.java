package com.example.demo_musicstreamfirebase.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.demo_musicstreamfirebase.models.MyExoplayer;
import com.example.demo_musicstreamfirebase.activities.PlayerActivity;
import com.example.demo_musicstreamfirebase.databinding.SongListItemRecyclerRowBinding;
import com.example.demo_musicstreamfirebase.models.SongModel;

import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.MyViewHolder> {

    private final List<String> songIdList;

    // Constructor để khởi tạo danh sách các song ID
    public SongsListAdapter(List<String> songIdList) {
        this.songIdList = songIdList;
    }

    // ViewHolder để giữ các view trong từng row
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final SongListItemRecyclerRowBinding binding;

        // Constructor để khởi tạo binding
        public MyViewHolder(SongListItemRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Ràng buộc dữ liệu với các view
        public void bindData(String songId) {
            FirebaseFirestore.getInstance().collection("songs")
                    .document(songId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        SongModel song = documentSnapshot.toObject(SongModel.class);
                        if (song != null) {
                            binding.songTitleTextView.setText(song.getTitle());
                            binding.songSubtitleTextView.setText(song.getSubtitle());
                            Glide.with(binding.songCoverImageView.getContext())
                                    .load(song.getCoverUrl())
                                    .apply(new RequestOptions().transform(new RoundedCorners(32)))
                                    .into(binding.songCoverImageView);

                            binding.getRoot().setOnClickListener(v -> {
                                MyExoplayer.startPlaying(binding.getRoot().getContext(), song);
                                Intent intent = new Intent(v.getContext(), PlayerActivity.class);
                                v.getContext().startActivity(intent);
                            });
                        }
                    });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SongListItemRecyclerRowBinding binding = SongListItemRecyclerRowBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(songIdList.get(position));
    }

    @Override
    public int getItemCount() {
        return songIdList.size();
    }
}
