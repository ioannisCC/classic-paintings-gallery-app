package com.ioannis.unipi.example.assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import HelperClasses.HomeAdapter.CategoriesAdapter;
import HelperClasses.HomeAdapter.CategoriesHelperClass;
import HelperClasses.HomeAdapter.FeaturedAdapter;


public class Dashboard extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RecyclerView featuredRecycler;
    RecyclerView categoriesRecycler;
    RecyclerView.Adapter adapter; // adapter for featured and categories recycler views
    private LinearLayout cardsContainer; // container for popular cards
    MediaPlayer mediaPlayer;
    ImageView soundToggleIcon;
    boolean isSoundOn = true;
    // for the new screen to know where the click came from // 0 for featured, 1 for popular, 2 for categories
    int type;


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
        // start playing music
        mediaPlayer.start();

        sharedPreferences = getSharedPreferences(getString(R.string.paintings_preferences), MODE_PRIVATE);

        soundToggleIcon = findViewById(R.id.sound_toggle_icon);

        // set click listener on the icon
        soundToggleIcon.setOnClickListener(v -> toggleSound());

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
        // release the MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void toggleSound() {
        if (isSoundOn) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // clear all SharedPreferences
            editor.apply();
            Toast.makeText(Dashboard.this, "Preferences Cleared",
                    Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            soundToggleIcon.setImageResource(R.drawable.sound_off);
        } else {
            mediaPlayer.start();
            soundToggleIcon.setImageResource(R.drawable.sound_on);
        }
        isSoundOn = !isSoundOn; // toggle the state
    }

    private void featuredRecycler() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Artwork> featuredArtworks = new ArrayList<>();
        int index = 0;
        boolean dataExists = false;

        // check and load saved data
        while (true) {
            String titleKey = getString(R.string.artwork_title_index_) + getString(R.string.featured_) + index;
            String descriptionKey = getString(R.string.artwork_description_index_) + getString(R.string.featured_) + index;
            String imageKey = getString(R.string.artwork_image_index_) + getString(R.string.featured_) + index;
            String artistKey = getString(R.string.artwork_artist_index) + getString(R.string.featured_) + index;
            String yearKey = getString(R.string.artwork_year_index) + getString(R.string.featured_) + index;
            String mediumKey = getString(R.string.artwork_medium_index) + getString(R.string.featured_) + index;
            String dimensionsKey = getString(R.string.artwork_dimensions_index) + getString(R.string.featured_) + index;
            String art_movementKey = getString(R.string.artwork_art_movement_index) + getString(R.string.featured_) + index;
            String locationKey = getString(R.string.artwork_location_index) + getString(R.string.featured_) + index;

            String title = sharedPreferences.getString(titleKey, null);
            String description = sharedPreferences.getString(descriptionKey, null);
            int image = sharedPreferences.getInt(imageKey, -1);
            String artist = sharedPreferences.getString(artistKey, null);
            String year = sharedPreferences.getString(yearKey, null);
            String medium = sharedPreferences.getString(mediumKey, null);
            String dimensions = sharedPreferences.getString(dimensionsKey, null);
            String art_movement = sharedPreferences.getString(art_movementKey, null);
            String location = sharedPreferences.getString(locationKey, null);

            if (title != null && description != null && image != -1) {
                featuredArtworks.add(new Artwork(image, title, description, artist, year,
                        medium, dimensions, art_movement, location));
                dataExists = true;
            } else {
                break;  // no more saved data
            }
            index++;
        }

        // if no saved data found, add default artworks and save them to SharedPreferences
        if (!dataExists) {
            featuredArtworks.add(new Artwork(R.drawable.mona_lisa,
                    getString(R.string.mona_lisa_title),
                    getString(R.string.mona_lisa_description), getString(R.string.leonardo_da_vinci),
                    "1503-1506 (probably continued until 1517)",
                    getString(R.string.oil_on_poplar_canvas), " 77 cm × 53 cm ",
                    getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.louvre_paris)));

            featuredArtworks.add(new Artwork(R.drawable.girl_with_a_pearl_earring,
                    getString(R.string.girl_with_a_pearl_earring_title),
                    getString(R.string.girl_with_a_pearl_earring_description),
                    getString(R.string.johannes_vermeer), "1665",
                    getString(R.string.oil_on_canvas), " 44.5 cm × 39 cm ",
                    getString(R.string.dutch_golden_age) + ", " + getString(R.string.tronie),
                    getString(R.string.mauritshuis_hague_netherlands)));

            featuredArtworks.add(new Artwork(R.drawable.the_last_supper,
                    getString(R.string.the_last_supper_title),
                    getString(R.string.the_last_supper_description), getString(R.string.leonardo_da_vinci),
                    "1495–1498",getString(R.string.tempera_on_gesso_pitch_mastic), " 460 cm × 880 cm ",
                    getString(R.string.high_art_movement) + " " + getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.santa_maria_delle_grazie_milan_italy)));

            featuredArtworks.add(new Artwork(R.drawable.impression_sunrise,
                    getString(R.string.impression_sunrise_title),
                    getString(R.string.impression_sunrise_description),
                    getString(R.string.claude_monet), "1872",
                    getString(R.string.oil_on_canvas), " 48 cm × 63 cm ",
                    getString(R.string.impressionism), getString(R.string.musee_d_orsay_paris)));

            featuredArtworks.add(new Artwork(R.drawable.the_persistence_of_memory,
                    getString(R.string.the_persistence_of_memory_title),
                    getString(R.string.the_persistence_of_memory_description),
                    getString(R.string.salvador_dali), "1931", getString(R.string.oil_on_canvas),
                    " 24 cm × 33 cm ", getString(R.string.surrealism),
                    getString(R.string.museum_modern_art_ny)));

            for (Artwork featuredArtwork : featuredArtworks) {
                editor.putString(getString(R.string.artwork_title_index_) +
                        getString(R.string.featured_) + index, featuredArtwork.getTitle());
                editor.putString(getString(R.string.artwork_description_index_)
                        + getString(R.string.featured_) + index, featuredArtwork.getDescription());
                editor.putInt(getString(R.string.artwork_image_index_)
                        + getString(R.string.featured_) + index, featuredArtwork.getImageResId());
                editor.putString(getString(R.string.artwork_artist_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getArtist());
                editor.putString(getString(R.string.artwork_year_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getYear());
                editor.putString(getString(R.string.artwork_medium_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getMedium());
                editor.putString(getString(R.string.artwork_dimensions_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getDimensions());
                editor.putString(getString(R.string.artwork_art_movement_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getArtMovement());
                editor.putString(getString(R.string.artwork_location_index)
                        + getString(R.string.featured_) + index, featuredArtwork.getLocation());                index++;
            }
            editor.apply();
        }

        adapter = new FeaturedAdapter(featuredArtworks, this);
        featuredRecycler.setAdapter(adapter);
    }

    // to be done
    private void categoriesRecycler() {

        type = 2;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categories = new ArrayList<>();
        categories.add(new CategoriesHelperClass(R.drawable.start,
                "@strings/category", "Category1"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                "@strings/category", "Category2"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                "@strings/category", "Category3"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                "@strings/category", "Category4"));
        categories.add(new CategoriesHelperClass(R.drawable.start,
                "@strings/category", "Category5"));

        adapter = new CategoriesAdapter((categories));
        categoriesRecycler.setAdapter(adapter);

        editor.apply();

    }

    //  load cards dynamically
    private void loadCards() {

        type = 1;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<Artwork> popularArtworks = new ArrayList<>();
        int index = 0;
        boolean dataExists = false;

        // while true cause we don't know beforehand how many artworks are there saved
        // check and load saved data
        while (true) {
            String titleKey = getString(R.string.artwork_title_index_) + getString(R.string.popular_) + index;
            String descriptionKey = getString(R.string.artwork_description_index_) + getString(R.string.popular_) + index;
            String imageKey = getString(R.string.artwork_image_index_) + getString(R.string.popular_) + index;
            String artistKey = getString(R.string.artwork_artist_index) + getString(R.string.popular_) + index;
            String yearKey = getString(R.string.artwork_year_index) + getString(R.string.popular_) + index;
            String mediumKey = getString(R.string.artwork_medium_index) + getString(R.string.popular_) + index;
            String dimensionsKey = getString(R.string.artwork_dimensions_index) + getString(R.string.popular_) + index;
            String art_movementKey = getString(R.string.artwork_art_movement_index) + getString(R.string.popular_) + index;
            String locationKey = getString(R.string.artwork_location_index) + getString(R.string.popular_) + index;

            String title = sharedPreferences.getString(titleKey, null);
            String description = sharedPreferences.getString(descriptionKey, null);
            int image = sharedPreferences.getInt(imageKey, -1);
            String artist = sharedPreferences.getString(artistKey, null);
            String year = sharedPreferences.getString(yearKey, null);
            String medium = sharedPreferences.getString(mediumKey, null);
            String dimensions = sharedPreferences.getString(dimensionsKey, null);
            String art_movement = sharedPreferences.getString(art_movementKey, null);
            String location = sharedPreferences.getString(locationKey, null);

            if (title != null && description != null && image != -1 && artist != null
                    && year != null && medium != null && dimensions != null
                    && art_movement != null && location != null) {

                popularArtworks.add(new Artwork(image, title, description, artist, year, medium,
                        dimensions, art_movement, location));

                dataExists = true;
            } else {
                break;  // no more saved data
            }
            index++;
        }

        // if no saved data found, add default artworks and save them to SharedPreferences
        if (!dataExists) {

            popularArtworks.add(new Artwork(R.drawable.around_the_neighborhood,
                    getString(R.string.around_the_neighborhood_title),
                    getString(R.string.around_the_neighborhood_description),
                    getString(R.string.gabriel_bodnariu), "2023",
                    getString(R.string.oil_on_canvas), " 150 cm x 150 cm ",
                    getString(R.string.expressionism) + ", " + getString(R.string.conceptual) + ", " + getString(R.string.surrealism),
                    getString(R.string.not_applicable)));

            popularArtworks.add(new Artwork(R.drawable.birth_of_venus,
                    getString(R.string.the_birth_of_venus_title),
                    getString(R.string.birth_of_venus_description),
                    getString(R.string.sandro_botticelli), "1484–1486",
                    getString(R.string.tempera_on_canvas), " 172.5 cm × 278.9 cm ",
                    getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.uffizi_florence)));

            popularArtworks.add(new Artwork(R.drawable.starry_night,
                    getString(R.string.the_starry_night_title),
                    getString(R.string.starry_night_description),
                    getString(R.string.vincent_van_gogh), "1889",
                    getString(R.string.oil_on_canvas), " 73.7 cm × 92.1 cm ",
                    getString(R.string.post_impressionism), getString(R.string.museum_modern_art_ny)));

            popularArtworks.add(new Artwork(R.drawable.guernica,
                    getString(R.string.guernica_title),
                    getString(R.string.guernica_description),
                    getString(R.string.pablo_picaso), "1937",
                    getString(R.string.oil_on_canvas), " 349.3 cm × 776.5 cm ",
                    getString(R.string.cubism) + ", " + getString(R.string.surrealism),
                    getString(R.string.museo_reina_sofia_madrid)));

            popularArtworks.add(new Artwork(R.drawable.napoleon_crossing_alps,
                    getString(R.string.napoleon_crossing_alps_title),
                    getString(R.string.napoleon_crossing_alps_description),
                    getString(R.string.jacques_louis_david), "1801",
                    getString(R.string.oil_on_canvas), " 261 cm × 221 cm ",
                    getString(R.string.neoclassical) + "/" + getString(R.string.contemporary) + " " + getString(R.string.portraiture),
                    getString(R.string.chateau_de_malmaison_rueil_malmaison)));


            for (Artwork popularArtwork : popularArtworks) {
                editor.putString(getString(R.string.artwork_title_index_) +
                        getString(R.string.popular_) + index, popularArtwork.getTitle());
                editor.putString(getString(R.string.artwork_description_index_)
                        + getString(R.string.popular_) + index, popularArtwork.getDescription());
                editor.putInt(getString(R.string.artwork_image_index_)
                        + getString(R.string.popular_) + index, popularArtwork.getImageResId());
                editor.putString(getString(R.string.artwork_artist_index)
                        + getString(R.string.popular_) + index, popularArtwork.getArtist());
                editor.putString(getString(R.string.artwork_year_index)
                        + getString(R.string.popular_) + index, popularArtwork.getYear());
                editor.putString(getString(R.string.artwork_medium_index)
                        + getString(R.string.popular_) + index, popularArtwork.getMedium());
                editor.putString(getString(R.string.artwork_dimensions_index)
                        + getString(R.string.popular_) + index, popularArtwork.getDimensions());
                editor.putString(getString(R.string.artwork_art_movement_index)
                        + getString(R.string.popular_) + index, popularArtwork.getArtMovement());
                editor.putString(getString(R.string.artwork_location_index)
                        + getString(R.string.popular_) + index, popularArtwork.getLocation());
                index++;
            }
            editor.apply();
        }

        index = 0;
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
        editor.apply();
    }



}