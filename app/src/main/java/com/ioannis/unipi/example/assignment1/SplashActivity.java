package com.ioannis.unipi.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import HelperClasses.HomeAdapter.Artwork;
import HelperClasses.HomeAdapter.ArtworksDatabase;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ArtworksDatabase artworksDatabase;
    List<Artwork> artworks;


    // variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView slogan;

    private static final int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // start screen animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        // hooks
        image = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        // assign animations
        image.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);

        artworksDatabase = ArtworksDatabase.getInstance(this);
        artworks = artworksDatabase.getAllArtworks(this);

        if (artworks != null && !artworks.isEmpty()) {
            Log.d("Splash Screen", "artworks not null neither empty");
        } else {

            artworks.add(new Artwork(R.drawable.mona_lisa,
                    getString(R.string.mona_lisa_title),
                    getString(R.string.mona_lisa_description), getString(R.string.leonardo_da_vinci),
                    "1503-1506 (probably continued until 1517)",
                    getString(R.string.oil_on_poplar_canvas), " 77 cm × 53 cm ",
                    getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.louvre_paris)));

            artworks.add(new Artwork(R.drawable.girl_with_a_pearl_earring,
                    getString(R.string.girl_with_a_pearl_earring_title),
                    getString(R.string.girl_with_a_pearl_earring_description),
                    getString(R.string.johannes_vermeer), "1665",
                    getString(R.string.oil_on_canvas), " 44.5 cm × 39 cm ",
                    getString(R.string.dutch_golden_age) + ", " + getString(R.string.tronie),
                    getString(R.string.mauritshuis_hague_netherlands)));

            artworks.add(new Artwork(R.drawable.the_last_supper,
                    getString(R.string.the_last_supper_title),
                    getString(R.string.the_last_supper_description), getString(R.string.leonardo_da_vinci),
                    "1495–1498", getString(R.string.tempera_on_gesso_pitch_mastic), " 460 cm × 880 cm ",
                    getString(R.string.high_art_movement) + " " + getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.santa_maria_delle_grazie_milan_italy)));

            artworks.add(new Artwork(R.drawable.impression_sunrise,
                    getString(R.string.impression_sunrise_title),
                    getString(R.string.impression_sunrise_description),
                    getString(R.string.claude_monet), "1872",
                    getString(R.string.oil_on_canvas), " 48 cm × 63 cm ",
                    getString(R.string.impressionism), getString(R.string.musee_d_orsay_paris)));

            artworks.add(new Artwork(R.drawable.the_persistence_of_memory,
                    getString(R.string.the_persistence_of_memory_title),
                    getString(R.string.the_persistence_of_memory_description),
                    getString(R.string.salvador_dali), "1931", getString(R.string.oil_on_canvas),
                    " 24 cm × 33 cm ", getString(R.string.surrealism),
                    getString(R.string.museum_modern_art_ny)));

            artworks.add(new Artwork(R.drawable.around_the_neighborhood,
                    getString(R.string.around_the_neighborhood_title),
                    getString(R.string.around_the_neighborhood_description),
                    getString(R.string.gabriel_bodnariu), "2023",
                    getString(R.string.oil_on_canvas), " 150 cm x 150 cm ",
                    getString(R.string.expressionism) + ", " + getString(R.string.conceptual) + ", " + getString(R.string.surrealism),
                    getString(R.string.not_applicable)));

            artworks.add(new Artwork(R.drawable.birth_of_venus,
                    getString(R.string.the_birth_of_venus_title),
                    getString(R.string.birth_of_venus_description),
                    getString(R.string.sandro_botticelli), "1484–1486",
                    getString(R.string.tempera_on_canvas), " 172.5 cm × 278.9 cm ",
                    getString(R.string.italian) + " " + getString(R.string.renaissance),
                    getString(R.string.uffizi_florence)));

            artworks.add(new Artwork(R.drawable.starry_night,
                    getString(R.string.the_starry_night_title),
                    getString(R.string.starry_night_description),
                    getString(R.string.vincent_van_gogh), "1889",
                    getString(R.string.oil_on_canvas), " 73.7 cm × 92.1 cm ",
                    getString(R.string.post_impressionism), getString(R.string.museum_modern_art_ny)));

            artworks.add(new Artwork(R.drawable.guernica,
                    getString(R.string.guernica_title),
                    getString(R.string.guernica_description),
                    getString(R.string.pablo_picaso), "1937",
                    getString(R.string.oil_on_canvas), " 349.3 cm × 776.5 cm ",
                    getString(R.string.cubism) + ", " + getString(R.string.surrealism),
                    getString(R.string.museo_reina_sofia_madrid)));

            artworks.add(new Artwork(R.drawable.napoleon_crossing_alps,
                    getString(R.string.napoleon_crossing_alps_title),
                    getString(R.string.napoleon_crossing_alps_description),
                    getString(R.string.jacques_louis_david), "1801",
                    getString(R.string.oil_on_canvas), " 261 cm × 221 cm ",
                    getString(R.string.neoclassical) + "/" + getString(R.string.contemporary) + " " + getString(R.string.portraiture),
                    getString(R.string.chateau_de_malmaison_rueil_malmaison)));

            for (Artwork artwork : artworks) {
                artworksDatabase.addArtwork(artwork, this);
            }

            Log.d("Creation of database Splash Screen", "Artworks added to database");

        }

        // go to next screen
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Dashboard.class);

                if (artworks != null && !artworks.isEmpty()) {
                    Artwork[] artworksArray = new Artwork[artworks.size()];
                    artworks.toArray(artworksArray);
                    intent.putExtra("artworks_array", artworksArray);
                    Log.d("SplashActivity", "Artworks size: " + artworks.size());
                    Log.d("SplashActivity", "Artworks array size: " + artworksArray.length);
                } else {
                    intent.putExtra("artworks_array", new Artwork[0]);
                    Log.d("SplashActivity", "Artworks array size: No size");

                }

                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}