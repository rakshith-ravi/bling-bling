package co.updn.blingbling.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.updn.blingbling.R;
import co.updn.blingbling.fragments.ImageFragment;
import co.updn.blingbling.utils.Constants;
import co.updn.blingbling.utils.Data;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.images)
    ViewPager viewPager;
    @BindView(R.id.images_indicator)
    LinearLayout pagerIndicator;

    @BindView(R.id.title)
    TextView title;

    int size;
    ImageFragment fragments[];
    ImageView dots[];

    Data.Category category;
    Data.Item item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        category = Data.categories.get(getIntent().getIntExtra(Constants.CATEGORY_KEY, -1));
        item = category.items.get(getIntent().getIntExtra(Constants.ITEM_NUMBER_KEY, -1));
        size = item.imageURLs.length;

        title.setText("Designer " + replaceLast(category.name, "s"));

        fragments = new ImageFragment[size];
        dots = new ImageView[size];
        for(int i = 0; i < size; i++) {
            fragments[i] = new ImageFragment();
            fragments[i].setImage(getIntent().getStringExtra(Constants.IMAGE_KEY + i));
            dots[i] = new ImageView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot, getTheme()));
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pagerIndicator.addView(dots[i], params);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot, getTheme()));
        } else {
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < size; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot, getTheme()));
                    } else {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot, getTheme()));
                } else {
                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private String replaceLast(String name, String s) {
        return name.substring(0, name.lastIndexOf(s));
    }

    @OnClick(R.id.customize_text_view)
    public void onCustomize(View view) {
        Intent intent = new Intent(DetailsActivity.this, CustomizeActivity.class);
        intent.putExtra(Constants.CATEGORY_KEY, getIntent().getIntExtra(Constants.CATEGORY_KEY, -1));
        intent.putExtra(Constants.ITEM_NUMBER_KEY, getIntent().getIntExtra(Constants.ITEM_NUMBER_KEY, -1));
        startActivity(intent);
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        super.onBackPressed();
    }

    @OnClick(R.id.basket)
    public void onBasket(View view) {
        Intent intent = new Intent(DetailsActivity.this, BasketActivity.class);
        startActivity(intent);
    }
}
