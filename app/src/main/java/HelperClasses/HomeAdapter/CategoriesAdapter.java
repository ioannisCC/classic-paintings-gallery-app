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
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    ArrayList<CategoriesHelperClass> categories;

    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categories) {
        this.categories = categories;
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
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        CategoriesHelperClass categoriesHelperClass = categories.get(position);

        holder.image.setImageResource(categoriesHelperClass.getImage());
        holder.title.setText(categoriesHelperClass.getTitle());
        holder.description.setText(categoriesHelperClass.getDescription());

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
            description = itemView.findViewById(R.id.categories_description);

        }
    }

}
