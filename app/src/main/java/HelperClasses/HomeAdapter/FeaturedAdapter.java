package HelperClasses.HomeAdapter;

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
public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<Artwork> artworks;
    Context context;
    int type = 0;

    public FeaturedAdapter(ArrayList<Artwork> artworks, Context context) {
        this.artworks = artworks;
        this.context = context;
    }

    // put the card on the recycler view
    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);

        return new FeaturedViewHolder(view);
    }

    // fill the contents of the card
    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        Artwork artwork = artworks.get(position);

        holder.image.setImageResource(artwork.getImageResId());
        holder.title.setText(artwork.getTitle());
        String text = artwork.getArtist() + ", " + artwork.getYear();
        holder.description.setText(text);

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
        return artworks.size();
    }

    // associate the xml layout with the code
    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, description;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            // hooks
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            description = itemView.findViewById(R.id.featured_description);
        }
    }

}
