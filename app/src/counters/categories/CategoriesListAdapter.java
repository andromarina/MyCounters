package counters.categories;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.MyCounters.R;
import model.Category;

import java.util.List;

/**
 * Created by mara on 1/4/15.
 */
public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder> {

    private List<Category> categoriesList;
    private CategoryClickListener listener;

    public CategoriesListAdapter(List<Category> categoriesList, CategoryClickListener listener) {
        this.categoriesList = categoriesList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        Category category = categoriesList.get(i);
        categoryViewHolder.categoryName.setText(category.getName());
        categoryViewHolder.categoryImage.setImageResource(CategoriesHelper.getImageId(category.getId()));
        categoryViewHolder.setColor(CategoriesHelper.getColor(category.getId()));
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);
        return new CategoryViewHolder(itemView, this.listener);
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView categoryImage;
        protected TextView categoryName;
        protected CardView cardView;
        private CategoryClickListener listener;

        public CategoryViewHolder(View v, CategoryClickListener listener) {
            super(v);
            categoryImage = (ImageView) v.findViewById(R.id.img_category);
            categoryName = (TextView) v.findViewById(R.id.txt_category);
            cardView = (CardView) v;
            cardView.setOnClickListener(this);
            this.listener = listener;
        }

        public void setColor(int color) {
            this.cardView.setCardBackgroundColor(color);
        }

        @Override
        public void onClick(View v) {
            listener.notifyClick(getAdapterPosition());
        }
    }
}

