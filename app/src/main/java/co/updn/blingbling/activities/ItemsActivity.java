package co.updn.blingbling.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.updn.blingbling.R;
import co.updn.blingbling.customviews.CategoriesViewHolder;
import co.updn.blingbling.utils.Constants;
import co.updn.blingbling.utils.Data;
import co.updn.blingbling.utils.RetroAPI;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemsActivity extends AppCompatActivity {

    @BindView(R.id.items_grid)
    RecyclerView recyclerView;

    @BindView(R.id.categories)
    Spinner categoriesSpinner;

    Data.Category category;

    Data.ArrayResource goldColorRes;
    Data.ArrayResource diamondShapeRes;
    Data.ArrayResource diamondColorRes;
    Data.ArrayResource diamondClarityRes;

    AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        goldColorRes = ((Data.ArrayResource)Data.findResourceByName("gold-color"));
        diamondShapeRes = ((Data.ArrayResource)Data.findResourceByName("diamond-shape"));
        diamondColorRes = ((Data.ArrayResource)Data.findResourceByName("diamond-color"));
        diamondClarityRes = ((Data.ArrayResource)Data.findResourceByName("diamond-clarity"));

        categoriesSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_text, Data.categories));
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = Data.categories.get(position);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final LayoutInflater inflater = getLayoutInflater();
        category = Data.findCategoryByName(getIntent().getStringExtra(Constants.CATEGORY_KEY));
        categoriesSpinner.setSelection(Data.categories.indexOf(category));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CategoriesViewHolder(inflater.inflate(R.layout.item_category_small, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CategoriesViewHolder categoriesViewHolder = (CategoriesViewHolder) holder;
                categoriesViewHolder.textView.setText(category.name);
                if(category.items.get(position).imageURLs.length > 0) {
                    Glide.with(ItemsActivity.this)
                            .load(category.items.get(position).imageURLs[0])
                            .asBitmap()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .asIs()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(categoriesViewHolder.icon);
                }
                final int pos = position;
                categoriesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ItemsActivity.this, DetailsActivity.class)
                                .putExtra(Constants.IMAGES_COUNT, category.items.get(pos).imageURLs.length)
                                .putExtra(Constants.CATEGORY_KEY, Data.categories.indexOf(category))
                                .putExtra(Constants.ITEM_NUMBER_KEY, pos);
                        for(int i = 0; i < category.items.get(pos).imageURLs.length; i++) {
                            intent.putExtra(Constants.IMAGE_KEY + i, category.items.get(pos).imageURLs[i]);
                        }
                        startActivity(intent);
                    }
                });
                categoriesViewHolder.itemView.setLongClickable(true);
                categoriesViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        dialog = new AlertDialog.Builder(ItemsActivity.this)
                                .setTitle("Edit values")
                                .setView(R.layout.dialog_edit_items)
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        if(dialog != null) {
                                            final Data.Item item = category.items.get(pos);

                                            final EditText goldCharges = (EditText) dialog.findViewById(R.id.gold_charges);
                                            final Spinner goldColor = (Spinner) dialog.findViewById(R.id.gold_color);
                                            final EditText goldPurity = (EditText) dialog.findViewById(R.id.gold_purity);
                                            final Spinner diamondShape = (Spinner) dialog.findViewById(R.id.diamond_shape);
                                            final EditText diamondWeight = (EditText) dialog.findViewById(R.id.diamond_carat_weight);
                                            final Spinner diamondColor = (Spinner) dialog.findViewById(R.id.diamond_color);
                                            final Spinner diamondClarity = (Spinner) dialog.findViewById(R.id.diamond_clarity);

                                            RetroAPI.NetworkCalls.modifyItem
                                                    (
                                                            item.id,
                                                            Float.parseFloat(goldCharges.getText().toString()) + "",
                                                            goldColorRes.values.get(goldColor.getSelectedItemPosition()),
                                                            Float.parseFloat(goldPurity.getText().toString()) + "",
                                                            diamondShapeRes.values.get(diamondShape.getSelectedItemPosition()),
                                                            Float.parseFloat(diamondWeight.getText().toString()) + "",
                                                            diamondColorRes.values.get(diamondColor.getSelectedItemPosition()),
                                                            diamondClarityRes.values.get(diamondClarity.getSelectedItemPosition())
                                                    )
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<JsonObject>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            Toast.makeText(ItemsActivity.this, "Error connection to server. Object not updated", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onNext(JsonObject jsonObject) {
                                                            if (jsonObject.get("success").getAsBoolean()) {
                                                                Toast.makeText(ItemsActivity.this, "Successfully updated the item!", Toast.LENGTH_SHORT).show();

                                                                item.goldCharges = Float.parseFloat(goldCharges.getText().toString());
                                                                item.goldColor = goldColorRes.values.get(goldColor.getSelectedItemPosition());
                                                                item.goldPurity = Float.parseFloat(goldPurity.getText().toString());

                                                                item.diamondShape = diamondShapeRes.values.get(diamondShape.getSelectedItemPosition());
                                                                item.diamondWeight = Float.parseFloat(diamondWeight.getText().toString());
                                                                item.diamondColor = diamondColorRes.values.get(diamondColor.getSelectedItemPosition());
                                                                item.diamondClarity = diamondClarityRes.values.get(diamondClarity.getSelectedItemPosition());
                                                            } else {
                                                                Toast.makeText(ItemsActivity.this, jsonObject.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                            dialogInterface.dismiss();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        Data.Item item = category.items.get(pos);

                        EditText goldCharges = (EditText) dialog.findViewById(R.id.gold_charges);
                        Spinner goldColor = (Spinner) dialog.findViewById(R.id.gold_color);
                        EditText goldPurity = (EditText) dialog.findViewById(R.id.gold_purity);
                        Spinner diamondShape = (Spinner) dialog.findViewById(R.id.diamond_shape);
                        EditText diamondWeight = (EditText) dialog.findViewById(R.id.diamond_carat_weight);
                        Spinner diamondColor = (Spinner) dialog.findViewById(R.id.diamond_color);
                        Spinner diamondClarity = (Spinner) dialog.findViewById(R.id.diamond_clarity);

                        goldCharges.setText(item.goldCharges + "");
                        goldColor.setSelection(goldColorRes.indexOf(item.goldColor));
                        goldPurity.setText(item.goldPurity + "");
                        diamondShape.setSelection(diamondShapeRes.indexOf(item.diamondShape));
                        diamondWeight.setText(item.diamondWeight + "");
                        diamondColor.setSelection(diamondColorRes.indexOf(item.diamondColor));
                        diamondClarity.setSelection(diamondClarityRes.indexOf(item.diamondClarity));

                        dialog.show();
                        return false;
                    }
                });
            }

            @Override
            public int getItemCount() {
                return category.items.size();
            }
        });
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        finish();
    }

    @OnClick(R.id.basket)
    public void onBasket(View view) {
        startActivity(new Intent(ItemsActivity.this, BasketActivity.class));
    }
}
