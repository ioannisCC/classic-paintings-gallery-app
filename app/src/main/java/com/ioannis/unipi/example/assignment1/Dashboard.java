package com.ioannis.unipi.example.assignment1;

import android.os.Bundle;

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
import HelperClasses.HomeAdapter.FeaturedHelperClass;

public class Dashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView categoriesRecycler;
    RecyclerView.Adapter adapter;


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

        // hooks
        featuredRecycler = findViewById(R.id.features_recycler);
        featuredRecycler();

        categoriesRecycler = findViewById(R.id.categories_recycler);
        categoriesRecycler();

    }

    private void featuredRecycler() {

        // load only the views which are visible to users
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredArtworks = new ArrayList<>();
        featuredArtworks.add(new FeaturedHelperClass(R.drawable.start, "title", "ladujsrfglasdfujgvhlsdfjkaghklasdfju"));
        featuredArtworks.add(new FeaturedHelperClass(R.drawable.start, "title", "ladujsrfglasdfujgvhlsdfjkaghklasdfju"));
        featuredArtworks.add(new FeaturedHelperClass(R.drawable.start, "title", "ladujsrfglasdfujgvhlsdfjkaghklasdfju"));
        featuredArtworks.add(new FeaturedHelperClass(R.drawable.start, "title", "ladujsrfglasdfujgvhlsdfjkaghklasdfju"));

        adapter = new FeaturedAdapter(featuredArtworks);
        featuredRecycler.setAdapter(adapter);

    }

    private void categoriesRecycler() {

        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoriesHelperClass> categories = new ArrayList<>();
        categories.add(new CategoriesHelperClass(R.drawable.start, "title", "guilasdeghuiasduio;agio;ghi;agh"));
        categories.add(new CategoriesHelperClass(R.drawable.start, "title", "guilasdeghuiasduio;agio;ghi;agh"));
        categories.add(new CategoriesHelperClass(R.drawable.start, "title", "guilasdeghuiasduio;agio;ghi;agh"));
        categories.add(new CategoriesHelperClass(R.drawable.start, "title", "guilasdeghuiasduio;agio;ghi;agh"));
        categories.add(new CategoriesHelperClass(R.drawable.start, "title", "guilasdeghuiasduio;agio;ghi;agh"));

        adapter = new CategoriesAdapter((categories));
        categoriesRecycler.setAdapter(adapter);

    }
}