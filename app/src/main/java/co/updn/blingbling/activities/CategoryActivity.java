package co.updn.blingbling.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.updn.blingbling.R;
import co.updn.blingbling.customviews.CategoriesViewHolder;
import co.updn.blingbling.utils.Constants;
import co.updn.blingbling.utils.Data;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.categories_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        final LayoutInflater inflater = getLayoutInflater();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public int getItemViewType(int position) {
                if(position >= getItemCount() - 3)
                    return 2;
                return 1;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType == 2)
                    return new CategoriesViewHolder(inflater.inflate(R.layout.item_category_text, parent, false));
                else
                    return new CategoriesViewHolder(inflater.inflate(R.layout.item_category_wide, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final CategoriesViewHolder categoriesViewHolder = (CategoriesViewHolder) holder;
                if(position < getItemCount() - 3) {
                    if (position % 2 == 1) {
                        Glide.with(CategoryActivity.this)
                                .load(Data.categories.get(position).imageURL)
                                .asBitmap()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .asIs()
                                .into(categoriesViewHolder.icon);
                        categoriesViewHolder.textView.setText(Data.categories.get(position).name);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) categoriesViewHolder.icon.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            params.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
                            params.addRule(RelativeLayout.ALIGN_PARENT_START);
                        }
                        categoriesViewHolder.textView.setGravity(Gravity.END);
                    } else {
                        Glide.with(CategoryActivity.this)
                                .load(Data.categories.get(position).imageURL)
                                .asBitmap()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .asIs()
                                .into(categoriesViewHolder.icon);
                        categoriesViewHolder.textView.setText(Data.categories.get(position).name);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) categoriesViewHolder.icon.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            params.addRule(RelativeLayout.ALIGN_PARENT_START, 0);
                            params.addRule(RelativeLayout.ALIGN_PARENT_END);
                        }
                        categoriesViewHolder.textView.setGravity(Gravity.START);
                    }
                    categoriesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ProgressDialog dialog = new ProgressDialog(CategoryActivity.this);
                            dialog.setIndeterminate(true);
                            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog.setMessage("Loading");
                            dialog.show();
                            Intent intent = new Intent(CategoryActivity.this, ItemsActivity.class)
                                    .putExtra(Constants.CATEGORY_KEY, Data.categories.get(categoriesViewHolder.getAdapterPosition()).name);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                } else {
                    if(getItemCount() - position == 3) {
                        categoriesViewHolder.textView.setText("Settings");
                    } else if(getItemCount() - position == 2) {
                        categoriesViewHolder.textView.setText("Sent quotations");
                    } else if(getItemCount() - position == 1) {
                        categoriesViewHolder.textView.setText("Logout");
                    }
                }
            }

            @Override
            public int getItemCount() {
                return Data.categories.size() + 3;
            }
        });
    }

    @OnClick(R.id.basket)
    public void onBasket(View view) {
        startActivity(new Intent(CategoryActivity.this, BasketActivity.class));
    }
}
