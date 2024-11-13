package HelperClasses.HomeAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioannis.unipi.example.assignment1.ArtDetailActivity;
import com.ioannis.unipi.example.assignment1.R;

import java.util.ArrayList;


// bridge between values and design
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    ArrayList<CategoriesHelperClass> categories;
    private Context context;
    private final int type = 2;


    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    public ArrayList<CategoriesHelperClass> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoriesHelperClass> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_design, parent, false);

        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CategoriesHelperClass categoriesHelperClass = categories.get(position);

        holder.image.setImageResource(categoriesHelperClass.getImage());
        holder.title.setText(categoriesHelperClass.getTitle());
        holder.description.setText(categoriesHelperClass.getDescription());

        // add click listener for each card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the new activity or handle the click
                Intent intent = new Intent(context, ArtDetailActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, description;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            // hooks
            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            description = itemView.findViewById(R.id.category_description);

        }
    }
}
