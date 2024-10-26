package com.ioannis.unipi.example.assignment1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;


public class ArtDetailActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageView artworkImage;
    TextView artworkTitle, artworkDescription;
    Animation topAnimation, bottomAnimation, scaleInAnimation, scaleOutAnimation;

    String title, description, artist, year, medium, dimensions, art_movement, location;
    int imageResId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_art_detail);

        sharedPreferences = getSharedPreferences(getString(R.string.paintings_preferences), MODE_PRIVATE);;

        // hooks
        artworkImage = findViewById(R.id.artworkImageDetail);
        artworkTitle = findViewById(R.id.artworkTitleDetail);
        artworkDescription = findViewById(R.id.artworkDescriptionDetail);

        int artworkIndex = getIntent().getIntExtra("index", -1);
        int artworkType = getIntent().getIntExtra("type", -1);
        // retrieve artwork details using the index
        if (artworkIndex != -1 && artworkType != -1) {
            if (artworkType == 0) {
                title = sharedPreferences.getString(getString(R.string.artwork_title_index_) +
                        getString(R.string.featured_) + artworkIndex, getString(R.string.default_title));
                description = sharedPreferences.getString(getString(R.string.artwork_description_index_) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_description));
                imageResId = sharedPreferences.getInt(getString(R.string.artwork_image_index_) +
                        getString(R.string.featured_)+ artworkIndex, R.drawable.start);
                artist = sharedPreferences.getString(getString(R.string.artwork_artist_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_artist));
                year = sharedPreferences.getString(getString(R.string.artwork_year_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_year));
                medium = sharedPreferences.getString(getString(R.string.artwork_medium_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_medium));
                dimensions = sharedPreferences.getString(getString(R.string.artwork_dimensions_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_dimensions));
                art_movement = sharedPreferences.getString(getString(R.string.artwork_art_movement_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_art_movement));
                location = sharedPreferences.getString(getString(R.string.artwork_location_index) +
                        getString(R.string.featured_)+ artworkIndex, getString(R.string.default_location));

            } else if (artworkType == 1) {
                title = sharedPreferences.getString(getString(R.string.artwork_title_index_) +
                        getString(R.string.popular_) + artworkIndex, getString(R.string.default_title));
                description = sharedPreferences.getString(getString(R.string.artwork_description_index_) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_description));
                imageResId = sharedPreferences.getInt(getString(R.string.artwork_image_index_) +
                        getString(R.string.popular_)+ artworkIndex, R.drawable.start);
                artist = sharedPreferences.getString(getString(R.string.artwork_artist_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_artist));
                year = sharedPreferences.getString(getString(R.string.artwork_year_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_year));
                medium = sharedPreferences.getString(getString(R.string.artwork_medium_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_medium));
                dimensions = sharedPreferences.getString(getString(R.string.artwork_dimensions_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_dimensions));
                art_movement = sharedPreferences.getString(getString(R.string.artwork_art_movement_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_art_movement));
                location = sharedPreferences.getString(getString(R.string.artwork_location_index) +
                        getString(R.string.popular_)+ artworkIndex, getString(R.string.default_location));
            }
            else {
                //to be done
                title = sharedPreferences.getString("artwork_category_categories_" + artworkIndex, "Default Title");
                description = sharedPreferences.getString("artwork_category_featured_" + artworkIndex, "Default Description");
                imageResId = sharedPreferences.getInt("artwork_image_category_" + artworkIndex, R.drawable.start);
            }
            // Set the retrieved data to the views
            artworkTitle.setText(title);
            artworkDescription.setText(description);
            artworkImage.setImageResource(imageResId);
        }

        // start screen animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        scaleInAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        scaleOutAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_out);

        // start animations
        artworkImage.startAnimation(scaleInAnimation);
        artworkDescription.setAnimation(bottomAnimation);

        // custom back pressed behavior using the dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                artworkImage.startAnimation(scaleOutAnimation);
                finish();
            }
        });
    }
}
