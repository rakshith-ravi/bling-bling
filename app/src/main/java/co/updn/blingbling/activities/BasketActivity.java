package co.updn.blingbling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.updn.blingbling.R;
import co.updn.blingbling.customviews.CategoriesViewHolder;
import co.updn.blingbling.utils.Data;

public class BasketActivity extends AppCompatActivity {

    @BindView(R.id.basket_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        final LayoutInflater inflater = getLayoutInflater();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CategoriesViewHolder(inflater.inflate(R.layout.item_category_small, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CategoriesViewHolder categoriesViewHolder = (CategoriesViewHolder) holder;
                categoriesViewHolder.textView.setText(Data.cart.get(position).name);
                Glide.with(BasketActivity.this)
                        .load(Data.cart.get(position).imageURLs[0])
                        .asBitmap()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .asIs()
                        .into(categoriesViewHolder.icon);
            }

            @Override
            public int getItemCount() {
                return Data.cart.size();
            }
        });
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        finish();
    }
}
