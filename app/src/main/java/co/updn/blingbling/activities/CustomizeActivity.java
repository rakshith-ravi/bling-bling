package co.updn.blingbling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.updn.blingbling.R;
import co.updn.blingbling.utils.Data;

public class CustomizeActivity extends AppCompatActivity {

    //Price
    @BindView(R.id.gold_price)
    EditText goldPrice;
    @BindView(R.id.making_charges)
    EditText makingCharges;
    @BindView(R.id.vat)
    EditText vat;

    //Gold
    @BindView(R.id.gold_charges)
    EditText goldCharges;
    @BindView(R.id.gold_color)
    Spinner goldColorSpinner;
    @BindView(R.id.gold_purity)
    EditText goldPurity;

    @BindView(R.id.diamond_shape)
    Spinner diamondShapeSpinner;
    @BindView(R.id.diamond_carat_weight)
    EditText diamondCaratWeight;
    @BindView(R.id.diamond_color)
    Spinner diamondColorSpinner;
    @BindView(R.id.diamond_clarity)
    Spinner diamondClaritySpinner;

    Data.ArrayResource goldColor;
    Data.ArrayResource diamondShape;
    Data.ArrayResource diamondColor;
    Data.ArrayResource diamondClarity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        ButterKnife.bind(this);
        goldColor = ((Data.ArrayResource)Data.findResourceByName("gold-color"));
        diamondShape = ((Data.ArrayResource)Data.findResourceByName("diamond-shape"));
        diamondColor = ((Data.ArrayResource)Data.findResourceByName("diamond-color"));
        diamondClarity = ((Data.ArrayResource)Data.findResourceByName("diamond-clarity"));
        goldColorSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_text_end, goldColor.values));
        diamondShapeSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_text_end, diamondShape.values));
        diamondColorSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_text_end, diamondColor.values));
        diamondClaritySpinner.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner_text_end, diamondClarity.values));
    }

    @OnClick(R.id.add_to_basket)
    public void onAddToBasket(View view) {
        try {
            Data.Item item = new Data.Item();

            item.goldPrice = Float.parseFloat(goldPrice.getText().toString());
            item.makingCharges = Float.parseFloat(makingCharges.getText().toString());
            item.vat = Float.parseFloat(vat.getText().toString());

            item.goldCharges = Float.parseFloat(goldCharges.getText().toString());
            item.goldColor = goldColor.values.get(goldColorSpinner.getSelectedItemPosition());
            item.goldPurity = Float.parseFloat(goldPurity.getText().toString());

            item.diamondShape = diamondShape.values.get(diamondShapeSpinner.getSelectedItemPosition());
            item.diamondWeight = Float.parseFloat(diamondCaratWeight.getText().toString());
            item.diamondColor = diamondColor.values.get(diamondColorSpinner.getSelectedItemPosition());
            item.diamondClarity = diamondClarity.values.get(diamondClaritySpinner.getSelectedItemPosition());

            Data.cart.add(item);
        } catch (Exception ex) {

        }
    }

    @OnClick(R.id.back)
    public void onBack(View view) {
        finish();
    }

    @OnClick(R.id.basket)
    public void onBasket(View view) {
        startActivity(new Intent(CustomizeActivity.this, BasketActivity.class));
    }
}
