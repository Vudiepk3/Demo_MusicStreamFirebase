package com.example.demo_musicstreamfirebase.models;

import android.content.Context;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.google.firebase.firestore.FirebaseFirestore;

public class  MyExoplayer {

    private static ExoPlayer exoPlayer = null;
    private static SongModel currentSong = null;

    // Trả về bài hát hiện tại đang phát
    public static SongModel getCurrentSong() {
        return currentSong;
    }

    // Trả về thể hiện của ExoPlayer
    public static ExoPlayer getInstance() {
        return exoPlayer;
    }

    // Bắt đầu phát bài hát mới
    public static void startPlaying(Context context, SongModel song) {
        if (exoPlayer == null)
            exoPlayer = new ExoPlayer.Builder(context).build();

        if (currentSong != song) {
            // Đây là bài hát mới nên bắt đầu phát
            currentSong = song;
            updateCount();
            String url = currentSong.getUrl();
            if (url != null) {
                MediaItem mediaItem = MediaItem.fromUri(url);
                exoPlayer.setMediaItem(mediaItem);
                exoPlayer.prepare();
                exoPlayer.play();
            }
        }
    }

    // Cập nhật số lượt phát của bài hát hiện tại
    public static void updateCount() {
        if (currentSong != null) {
            String id = currentSong.getId();
            if (id != null) {
                FirebaseFirestore.getInstance().collection("songs")
                        .document(id)
                        .get().addOnSuccessListener(documentSnapshot -> {
                            Long latestCount = documentSnapshot.getLong("count");
                            if (latestCount == null) {
                                latestCount = 1L;
                            } else {
                                latestCount += 1;
                            }

                            FirebaseFirestore.getInstance().collection("songs")
                                    .document(id)
                                    .update("count", latestCount);
                        });
            }
        }
    }
}
