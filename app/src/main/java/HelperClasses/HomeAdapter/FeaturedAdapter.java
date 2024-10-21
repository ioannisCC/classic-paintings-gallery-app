package HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioannis.unipi.example.assignment1.R;

import java.util.ArrayList;

// bridge between values and design
public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedHelperClass> featuredArtworks;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredArtworks) {
        this.featuredArtworks = featuredArtworks;
    }

    public ArrayList<FeaturedHelperClass> getFeaturedArtworks() {
        return featuredArtworks;
    }

    public void setFeaturedArtworks(ArrayList<FeaturedHelperClass> featuredArtworks) {
        this.featuredArtworks = featuredArtworks;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        FeaturedHelperClass featuredHelperClass = featuredArtworks.get(position);

        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getTitle());
        holder.description.setText(featuredHelperClass.getDescription());

    }

    @Override
    public int getItemCount() {
        return featuredArtworks.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, description;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            // hooks
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            description = itemView.findViewById(R.id.categories_description);

        }
    }

}
