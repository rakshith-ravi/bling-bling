package co.updn.blingbling.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import co.updn.blingbling.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView textView;

    public CategoriesViewHolder(View itemView) {
        super(itemView);
        icon = ButterKnife.findById(itemView, R.id.categories_icon);
        textView = ButterKnife.findById(itemView, R.id.categories_category_type);
    }
}
