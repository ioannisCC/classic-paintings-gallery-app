package com.ioannis.unipi.example.assignment1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.Locale;

import HelperClasses.HomeAdapter.Artwork;
import HelperClasses.HomeAdapter.ArtworksDatabase;
import HelperClasses.HomeAdapter.MediaPlayerManager;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ArtDetailActivity extends AppCompatActivity {

    private ImageView artworkImage;
    private ImageView glassBackground, tts_image;
    private TextView artworkTitle, artworkDescription;
    private Animation scaleOutAnimation;
    private TTS tts;
    private FloatingActionButton musicToggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_art_detail);

//        sharedPreferences = getSharedPreferences(getString(R.string.paintings_preferences), MODE_PRIVATE);

        // database
        //    SharedPreferences sharedPreferences;
        ArtworksDatabase artworksDatabase = ArtworksDatabase.getInstance(this);

        // hooks
        artworkImage = findViewById(R.id.artworkImageDetail);
        artworkTitle = findViewById(R.id.artworkTitleDetail);
        artworkDescription = findViewById(R.id.artworkDescriptionDetail);
        ImageView backgroundImage = findViewById(R.id.backgroundImage);
//        glassBackground = findViewById(R.id.glassBackground); // to do
        musicToggleButton = findViewById(R.id.musicToggleButton);
        tts_image = findViewById(R.id.tts);

        // set click listener on the icon
        musicToggleButton.setOnClickListener(v -> MediaPlayerManager.toggleSound(musicToggleButton));

        int artworkIndex = getIntent().getIntExtra(getString(R.string.index), -1);
        int artworkType = getIntent().getIntExtra(getString(R.string.type), -1);

        // retrieve artwork details using the index
        if (artworkIndex != -1 && artworkType != -1) {
            Artwork artwork = artworksDatabase.getArtwork(artworkIndex+1, this); // +1 so that indexing works

            // set the retrieved data to the views
            artworkTitle.setText(artwork.getTitle());
            artworkDescription.setText(artwork.getDescription());
            artworkImage.setImageResource(artwork.getImageResId());
            // set a blurred version of the artwork as the background with Glide
            Glide.with(this).load(artwork.getImageResId())
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 30)))
                    .into(backgroundImage);
        }

        // start screen animations
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        Animation scaleInAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        scaleOutAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_out);

        // start animations
        artworkTitle.startAnimation(topAnimation);
        artworkImage.startAnimation(scaleInAnimation);
        artworkDescription.setAnimation(bottomAnimation);

        tts = new TTS(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
                tts.setPitch(1.5f);
                tts.setSpeechRate(1.0f);
            }

            // set up the TTS listener once
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    if ("unique_utterance_id".equals(utteranceId)) {
                        Log.d("TTS", "TTS finished speaking");
                        runOnUiThread(() -> {
                            MediaPlayerManager.resumeMedia(); // resume the music when TTS finishes
                            tts_image.setImageResource(R.drawable.tts_on); // reset to TTS on image
                        });
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    Log.e("initTTSartDetail", "Error Occured");
                }
            });

        });

        tts_image.setOnClickListener(v -> {
            if (tts.isSpeaking()) {
                tts.stop();
                tts_image.setImageResource(R.drawable.tts_on); // change image to off
                MediaPlayerManager.resumeMedia(); // resume the music
            } else {
                String description = artworkDescription.getText().toString();
                MediaPlayerManager.pauseMedia(); // stop the music
                tts_image.setImageResource(R.drawable.tts_off); // change image to on
                tts.speak(description, TextToSpeech.QUEUE_FLUSH, null, "unique_utterance_id"); // pass a unique ID
            }
        });

        // custom back pressed behavior using the dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // animation
                artworkImage.startAnimation(scaleOutAnimation);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdownTTS();
    }
}
