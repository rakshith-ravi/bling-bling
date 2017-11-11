package co.updn.blingbling.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class MontserratTextView extends TextView {
    public MontserratTextView(Context context) {
        super(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        setTypeface(typeface);
    }

    public MontserratTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        setTypeface(typeface);
    }

    public MontserratTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        setTypeface(typeface);
    }
}
