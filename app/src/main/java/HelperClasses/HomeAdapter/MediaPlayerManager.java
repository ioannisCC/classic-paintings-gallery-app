package HelperClasses.HomeAdapter;

import android.content.Context;
import android.media.MediaPlayer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ioannis.unipi.example.assignment1.R;

public class MediaPlayerManager {

    private static MediaPlayer mediaPlayer;

    public static void initializeMediaPlayer(Context context, int resId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId);
        }
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void pauseMedia() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void resumeMedia() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static void toggleSound(FloatingActionButton musicToggleButton) {
        if (MediaPlayerManager.getMediaPlayer().isPlaying()) {
            MediaPlayerManager.pauseMedia();
            musicToggleButton.setImageResource(R.drawable.sound_off);
        } else {
            MediaPlayerManager.resumeMedia();
            musicToggleButton.setImageResource(R.drawable.sound_on);
        }
    }

    public static void releaseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
