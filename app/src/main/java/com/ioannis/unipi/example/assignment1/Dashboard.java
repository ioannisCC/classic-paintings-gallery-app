package com.ioannis.unipi.example.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HomeAdapter.Artwork;
import HelperClasses.HomeAdapter.CategoriesAdapter;
import HelperClasses.HomeAdapter.CategoriesHelperClass;
import HelperClasses.HomeAdapter.FeaturedAdapter;


public class Dashboard extends AppCompatActivity {

    private List<Artwork> artworks;
    private RecyclerView featuredRecycler;
    private RecyclerView categoriesRecycler;
    private RecyclerView.Adapter adapter; // adapter for featured and categories recycler views
    private LinearLayout cardsContainer; // container for popular cards
    private MediaPlayer mediaPlayer;
    private ImageView soundToggleIcon;

    // for the new screen to know where the click came from 0 for featured, 1 for popular, 2 for categories (when SharedPreferences where used)
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.beethoven_moonlight_sonata);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        soundToggleIcon = findViewById(R.id.sound_toggle_icon);

        // set click listener on the icon
        soundToggleIcon.setOnClickListener(v -> toggleSound());

        // make the array parcelable so it can be passed from an intent to another
        Parcelable[] parcelables = getIntent().getParcelableArrayExtra("artworks_array");

        if (parcelables != null && parcelables.length > 0) {
            artworks = new ArrayList<>();
            for (Parcelable parcelable : parcelables) {
                if (parcelable instanceof Artwork) {
                    artworks.add((Artwork) parcelable);
                }
            }
        }

        // hooks
        featuredRecycler = findViewById(R.id.features_recycler);
        featuredRecycler();

        categoriesRecycler = findViewById(R.id.categories_recycler);
        categoriesRecycler();

        // find the included layout by its ID
        cardsContainer = findViewById(R.id.cards_container);

        loadCards();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void toggleSound() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            soundToggleIcon.setImageResource(R.drawable.sound_off);
        } else {
            mediaPlayer.start();
            soundToggleIcon.setImageResource(R.drawable.sound_on);
        }
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Artwork> featuredArtworks = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                String title = (artworks.get(i)).getTitle();
                String description = (artworks.get(i)).getDescription();
                int imageResId = (artworks.get(i)).getImageResId();

                if (title != null && description != null && imageResId != -1) {
                    // add to featured artworks list
                    featuredArtworks.add(artworks.get(i));
                } else {
                    // no more valid data
                    break;
                }
            }
        adapter = new FeaturedAdapter(featuredArtworks, this);
        featuredRecycler.setAdapter(adapter);
    }

    // to be done
    private void categoriesRecycler() {

        type = 2;
//        SharedPreferences.Editor editor = sharedPreferences.edit();

        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categories = new ArrayList<>();
        categories.add(new CategoriesHelperClass(R.drawable.start,
                getString(R.string.category), "Category1"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                getString(R.string.category), "Category2"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                getString(R.string.category), "Category3"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                getString(R.string.category), "Category4"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                getString(R.string.category), "Category5"));

        adapter = new CategoriesAdapter((categories));
        categoriesRecycler.setAdapter(adapter);

//        editor.apply();

    }

    //  load cards dynamically
    private void loadCards() {

        type = 1;

        ArrayList<Artwork> popularArtworks = new ArrayList<>();
        int index = 5;
        boolean dataExists = false;

        // while true cause we don't know beforehand how many artworks are there saved
        // check and load saved data
        while (index < artworks.size()) {
                String title = artworks.get(index).getTitle();
                String description = artworks.get(index).getDescription();
                int imageResId = artworks.get(index).getImageResId();

                if (title != null && description != null && imageResId != -1) {
                    // add to featured artworks list
                    popularArtworks.add(artworks.get(index));
                    index++;
                } else {
                    // no more valid data
                    break;
                }
        }


        index = 5;
        for (Artwork popularArtwork : popularArtworks) {

            // inflate the card layout
            View cardView = getLayoutInflater().inflate(R.layout.popular_card_design,
                    cardsContainer, false);

            // find the views in the card layout
            ImageView artworkImage = cardView.findViewById(R.id.artworkImage);
            TextView artworkTitle = cardView.findViewById(R.id.artworkTitle);
            TextView artworkDescription = cardView.findViewById(R.id.artworkDescription);

            // set the data for the card
            artworkImage.setImageResource(popularArtwork.getImageResId());
            artworkTitle.setText(popularArtwork.getTitle());
            String text = popularArtwork.getArtist() + ", " + popularArtwork.getYear() + "\n" +
                    popularArtwork.getMedium()+ ", " + popularArtwork.getDimensions() + "\n";
            artworkDescription.setText(text);

            // set a click listener on the card view to pass them on the new screen
            int finalIndex = index;
            cardView.setOnClickListener(v -> {
                Log.d("CardClick", "Card Clicked: " + popularArtwork.getTitle()); // debug
                Intent intent = new Intent(Dashboard.this, ArtDetailActivity.class);
                intent.putExtra(getString(R.string.index), finalIndex);
                intent.putExtra(getString(R.string.type), type);
                startActivity(intent);
            });

            // add the card view to the container
            cardsContainer.addView(cardView);
            index++;
        }
    }

}